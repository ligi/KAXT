package org.ligi.kaxt


import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Build

fun AlarmManager.setExactAndAllowWhileIdleCompat(type: Int, triggerAtMillis: Long, operation: PendingIntent) {
    if (Build.VERSION.SDK_INT >= 23) {
        setExactAndAllowWhileIdle(type, triggerAtMillis, operation)
    } else if (Build.VERSION.SDK_INT >= 19) {
        setExact(type, triggerAtMillis, operation)
    } else {
        set(type, triggerAtMillis, operation)
    }
}