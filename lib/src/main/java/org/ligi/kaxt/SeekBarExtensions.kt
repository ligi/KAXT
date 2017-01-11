package org.ligi.kaxt

import android.widget.SeekBar

fun SeekBar.doOnProgressChanged(function: (progress: Int, fromUser: Boolean) -> Unit) = setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = function(progress, fromUser)

})