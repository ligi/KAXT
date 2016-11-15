package org.ligi.kaxt


import android.content.Context
import android.net.Uri
import java.io.File

fun Uri.loadImage(context: Context): File? {
    val filePath = ImageFromIntentUriToFileConverter(context).extract(this)
    return filePath?.let(::File)
}
