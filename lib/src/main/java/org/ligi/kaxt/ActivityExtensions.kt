package org.ligi.kaxt


import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * dynamically disable rotation
 * to be used in onCreate
 * slightly modified from http://stackoverflow.com/a/8765901/322642
 */
fun Activity.disableRotation() = lockOrientation(this.resources.configuration.orientation)

fun Activity.lockOrientation(orientation: Int) {
    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            val rotation = this.windowManager.defaultDisplay.rotation
            if (rotation == android.view.Surface.ROTATION_90 || rotation == android.view.Surface.ROTATION_180) {
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
            } else {
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            val rotation = this.windowManager.defaultDisplay.rotation
            if (rotation == android.view.Surface.ROTATION_0 || rotation == android.view.Surface.ROTATION_90) {
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
            }
        }
    }//when
}

/**
 * dynamically enable rotation
 * counterpart to enableRotation
 * slightly modified from http://stackoverflow.com/a/8765901/322642
 */
fun Activity.enableRotation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}

fun Activity.closeKeyboard() = currentFocus?.let {
    getInputMethodManager().hideSoftInputFromWindow(it.windowToken, 0)
}

fun Activity.inflate(@LayoutRes layout: Int, root: ViewGroup? = null) : View = LayoutInflater.from(this).inflate(layout, root)

