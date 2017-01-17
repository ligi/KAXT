package org.ligi.kaxt


import android.view.View

fun View.setVisibility(visible: Boolean, invisibleVisibility: Int = View.GONE) = if (visible) {
    setVisibility(View.VISIBLE)
} else {
    setVisibility(invisibleVisibility)
}
