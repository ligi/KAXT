package org.ligi.kaxt

import android.annotation.TargetApi
import android.app.*
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
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlin.reflect.KClass


@ColorInt
fun Context.getThemeColor(@AttrRes res: Int) = TypedValue().let {
    theme.resolveAttribute(res, it, true)
    it.data
}

fun Context.startActivityFromClass(activityClass: Class<out Activity>) = startActivity(Intent(this, activityClass))
fun Context.startActivityFromClass(activityClass: KClass<out Activity>) = startActivity(Intent(this, activityClass.java))

fun Context.startActivityFromURL(uri: Uri) = try {
    startActivity(Intent(Intent.ACTION_VIEW, uri))
} catch (e: ActivityNotFoundException) {
    Toast.makeText(this, "No Browser found", Toast.LENGTH_LONG).show()
}

fun Context.startActivityFromURL(url: String) = startActivityFromURL(Uri.parse(url))

fun Context.getActivityManager() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
fun Context.getAlarmManager() = getSystemService(Context.ALARM_SERVICE) as AlarmManager
fun Context.getConnectivityManager() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
fun Context.getDownloadManager() = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
fun Context.getSensorManager() = getSystemService(Context.SENSOR_SERVICE) as SensorManager
fun Context.getInputMethodManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
fun Context.getNotificationManager() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
fun Context.getPowerManager() = getSystemService(Context.POWER_SERVICE) as PowerManager
fun Context.getVibrator() = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

@TargetApi(19)
fun Context.getPrintService() = getSystemService(Context.PRINT_SERVICE) as PrintManager

@TargetApi(11)
fun Context.getClipboardManager() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


