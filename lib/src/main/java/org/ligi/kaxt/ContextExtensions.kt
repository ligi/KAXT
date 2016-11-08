package org.ligi.kaxt

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast


fun Context.startActivityFromClass(activityClass: Class<Activity>) {
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
