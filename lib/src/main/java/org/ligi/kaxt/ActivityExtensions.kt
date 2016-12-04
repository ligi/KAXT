package org.ligi.kaxt


import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build

/**
 * dynamically disable rotation
 * to be used in onCreate
 * slightly modified from http://stackoverflow.com/a/8765901/322642
 */
fun Activity.disableRotation() = lockOrientation(this.resources.configuration.orientation)

fun Activity.lockOrientation(orientation: Int) {
    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO) {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            val rotation = this.windowManager.defaultDisplay.rotation
            if (rotation == android.view.Surface.ROTATION_90 || rotation == android.view.Surface.ROTATION_180) {
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
            } else {
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

        Configuration.ORIENTATION_LANDSCAPE -> if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO) {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
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

fun Activity.recreateWhenPossible() {
    if (Build.VERSION.SDK_INT >= 11) {
        recreate()
    }
}

fun Activity.closeKeyboard() = currentFocus?.let {
    getInputMethodManager().hideSoftInputFromWindow(it.windowToken, 0)
}

