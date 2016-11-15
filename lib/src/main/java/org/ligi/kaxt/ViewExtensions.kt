package org.ligi.kaxt


import android.view.View

fun View.setVisibility(visible: Boolean, invisibleVisibility: Int = View.GONE) = if (visible) {
    this.setVisibility(View.VISIBLE)
} else {
    this.setVisibility(invisibleVisibility)
}
