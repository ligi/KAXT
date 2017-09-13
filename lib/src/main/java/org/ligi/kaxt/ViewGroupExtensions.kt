package org.ligi.kaxt

import android.view.View
import android.view.ViewGroup

fun ViewGroup.withAllChildren(function: (view: View) -> Unit) {
    (0 until childCount).forEach { i ->
        val child = getChildAt(i)
        if (child is ViewGroup) {
            child.withAllChildren(function)
        } else {
            function(child)
        }
    }
}