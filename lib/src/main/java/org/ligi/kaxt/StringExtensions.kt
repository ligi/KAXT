package org.ligi.kaxt


import android.graphics.Color
import java.util.regex.Pattern

fun String?.parseColor(defaultValue: Int) : Int = when {
    this == null -> defaultValue

    this.startsWith("rgb") -> {
        parseColorRGBStyle(this, defaultValue)
    }

    this.startsWith("#") -> {
        try {
            Color.parseColor(this)
        } catch (ignored: Exception) {
            // fall through to default color
            defaultValue
        }
    }

    else -> defaultValue
}


private fun parseColorRGBStyle(color_str: String, defaultValue: Int): Int {
    val pattern = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)")
    val matcher = pattern.matcher(color_str)

    if (matcher.matches()) {
        return 255 shl 24 or
                (Integer.valueOf(matcher.group(1))!! shl 16) or // r
                (Integer.valueOf(matcher.group(2))!! shl 8) or // g
                Integer.valueOf(matcher.group(3))!! // b

    }

    return defaultValue
}


