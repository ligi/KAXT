package org.ligi.kaxt


import android.app.Activity
import android.content.ComponentName
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration

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


/**
 * sometimes you catch an intent that you did not really want or you have problems to process it
 */
fun Activity.rethrowIntentExcludingSelf() {
    val component = ComponentName(this, this.javaClass)
    packageManager.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)

    try {
        val intent = this.intent
        intent.component = null
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        // might be activity not found in the case of no browser installed - just be really careful here - otherwise we might end up with the Activity disabled forever
    } finally {
        android.os.Handler().postDelayed({
            packageManager.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
            finish()
        }, 250)

    }
}

