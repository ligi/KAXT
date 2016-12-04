package org.ligi.kaxt

import android.app.Activity
import android.app.ActivityManager
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.PowerManager
import android.os.Vibrator
import android.print.PrintManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


fun Context.startActivityFromClass(activityClass: Class<out Activity>) {
    this.startActivity(Intent(this, activityClass))
}

fun Context.startActivityFromURL(url: String) = this.startActivityFromURL(Uri.parse(url))
fun Context.startActivityFromURL(url: Uri) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = url
        this.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "No Browser found", Toast.LENGTH_LONG).show()
    }
}

fun Context.getNotificationManager() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
fun Context.getClipboardManager() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
fun Context.getPowerManager() = getSystemService(Context.POWER_SERVICE) as PowerManager
fun Context.getActivityManager() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
fun Context.getInputMethodManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
fun Context.getVibrator() = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
fun Context.getDownloadManager() = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
fun Context.getConnectivityManager() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
fun Context.getPrintService() = getSystemService(Context.PRINT_SERVICE) as PrintManager
fun Context.getSensorManager() = getSystemService(Context.SENSOR_SERVICE) as SensorManager



