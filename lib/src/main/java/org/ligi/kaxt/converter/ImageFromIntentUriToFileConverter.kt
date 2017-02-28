package org.ligi.kaxt.converter

import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL

class ImageFromIntentUriToFileConverter(internal val context: Context) {

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.

     * @param uri     The Uri to query.
     * *
     * @author paulburke
     */
    fun extract(uri: Uri): String? {

        var result: String? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            result = handleKitKat(context, uri)
        }

        if (result == null && "content".equals(uri.scheme, ignoreCase = true)) {
            result = getDataColumn(context, uri, null, null)
        }

        if (result == null) {
            val extract = extractFile(uri)
            if (extract != null) {
                result = extract.absolutePath
            }
        }

        if (result == null && "file".equals(uri.scheme, ignoreCase = true)) {
            result = uri.path
        }

        return result
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun handleKitKat(context: Context, uri: Uri): String? {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
                val type = split[0]

                val contentUri = when(type) {
                    "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    else -> null
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                if (contentUri!=null) {
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            }// MediaProvider
            // DownloadsProvider
        }
        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.

     * @param context       The context.
     * *
     * @param uri           The Uri to query.
     * *
     * @param selection     (Optional) Filter used in the query.
     * *
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * *
     * @return The value of the _data column, which is typically a file path.
     */
    private fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * *
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * *
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * *
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }


    private fun extractFile(_selectedImage: Uri): File? {
        var selectedImage = _selectedImage
        val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME)
        val cursor = context.contentResolver.query(selectedImage, filePathColumn, null, null, null)
        // some devices (OS versions return an URI of com.android instead of com.google.android
        if (selectedImage.toString().startsWith("content://com.android.gallery3d.provider")) {
            // use the com.google provider, not the com.android provider.
            selectedImage = Uri.parse(selectedImage.toString().replace("com.android.gallery3d", "com.google.android.gallery3d"))
        }
        if (cursor != null) {
            cursor.moveToFirst()
            var columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA)
            // if it is a picasa image on newer devices with OS 3.0 and up
            if (selectedImage.toString().startsWith("content://com.google.android.gallery3d")) {
                columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
                if (columnIndex != -1) {
                    // Do this in a background thread, since we are fetching a large image from the web

                    return getBitmap("image_file_name.jpg", selectedImage)

                }
            } else { // it is a regular local image file
                var filePath: String? = cursor.getString(columnIndex)

                // taken from http://stackoverflow.com/questions/20067508/get-real-path-from-uri-android-kitkat-new-storage-access-framework
                if (filePath == null && Build.VERSION.SDK_INT >= 19) {
                    filePath = getFilePathForKITKAT(selectedImage)
                }
                cursor.close()
                return File(filePath!!)
            }
        } else if (selectedImage.toString().isNotEmpty()) {
            // Do this in a background thread, since we are fetching a large image from the web
            return getBitmap("image_file_name.jpg", selectedImage)
        }// If it is a picasa image on devices running OS prior to 3.0
        return null
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun getFilePathForKITKAT(selectedImage: Uri): String? {
        val wholeID = DocumentsContract.getDocumentId(selectedImage)

        if (File(wholeID).exists()) {
            return wholeID
        }
        val id: String
        if (wholeID.contains(":"))
            id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        else
            id = wholeID

        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"

        val innerCursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, arrayOf(id), null)

        val columnIndex = innerCursor!!.getColumnIndex(column[0])

        if (innerCursor.moveToFirst()) {
            return innerCursor.getString(columnIndex)
        }
        return null
    }


    private fun getBitmap(tag: String, url: Uri): File? {
        val cacheDir = context.cacheDir

        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }

        val outputFile = File(cacheDir, tag)

        try {
            getInputStreamByURL(url).copyTo(FileOutputStream(outputFile))
            return outputFile
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }

    }

    @Throws(IOException::class)
    private fun getInputStreamByURL(url: Uri): InputStream {
        if (url.toString().startsWith("content://com.google.android.gallery3d")) {
            return context.contentResolver.openInputStream(url)
        } else {
            return URL(url.toString()).openStream()
        }
    }

}
