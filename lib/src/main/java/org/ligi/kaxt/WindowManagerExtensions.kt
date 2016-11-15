package org.ligi.kaxt

import android.graphics.Point
import android.os.Build
import android.view.WindowManager

fun WindowManager.getSizeAsPointCompat(): Point {
    val result = Point()
    if (Build.VERSION.SDK_INT > 12) {
        defaultDisplay.getSize(result)
    } else {
        @Suppress("DEPRECATION")
        result.set(defaultDisplay.width, defaultDisplay.height)
    }
    return result
}

fun WindowManager.getLargestSide(): Int {
    val sizeAsPoint = getSizeAsPointCompat()
    return Math.max(sizeAsPoint.x, sizeAsPoint.y)
}


fun WindowManager.getSmallestSide(): Int {
    val sizeAsPoint = getSizeAsPointCompat()
    return Math.min(sizeAsPoint.x, sizeAsPoint.y)
}