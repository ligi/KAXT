package org.ligi.kaxt

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

/**
 * Indicates whether the specified action can be used as an intent. This
 * method queries the package manager for installed packages that can
 * respond to an intent with the specified action. If no suitable package is
 * found, this method returns false.

 * @param pm
 * *
 * @return
 */

fun Intent.isIntentAvailable(pm: PackageManager, flags: Int = PackageManager.MATCH_DEFAULT_ONLY)
        = pm.queryIntentActivities(this, flags).size > 0

/**
 * Indicates whether the specified action can be used as an service. This
 * method queries the package manager for installed packages that can
 * respond to an service with the specified action. If no suitable package is
 * found, this method returns false.

 * @param pm
 * *
 * @return
 */
fun Intent.isServiceAvailable(pm: PackageManager, flags: Int = PackageManager.MATCH_DEFAULT_ONLY)
    = pm.queryIntentServices(this, flags).size > 0

fun Intent.makeExplicit(context: Context): Intent? {
    val pm = context.packageManager
    val resolveInfo = pm.queryIntentServices(this, 0) ?: return null

    val serviceInfo = resolveInfo.get(0)
    val packageName = serviceInfo.serviceInfo.packageName
    val className = serviceInfo.serviceInfo.name
    val component = ComponentName(packageName, className)

    val result = Intent(this)
    result.component =component
    return result
}

