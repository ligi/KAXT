package org.ligi.kaxt

import android.graphics.Point
import android.view.WindowManager

fun WindowManager.getSizeAsPointCompat(): Point {
    val result = Point()
    defaultDisplay.getSize(result)
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