package org.ligi.kaxt

import android.graphics.Color
import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.regex.Pattern

fun String.encodeURL(encoding: String = "utf-8"): String = URLEncoder.encode(this, encoding)
fun String.decodeURL(encoding: String = "utf-8"): String = URLDecoder.decode(this, encoding)

fun String.toBase64(): String = Base64.encodeToString(toByteArray(), Base64.DEFAULT)
fun String.fromBase64() = String(Base64.decode(this, Base64.DEFAULT))

fun String?.parseColor(defaultValue: Int) : Int = when {
    this == null -> defaultValue

    startsWith("rgb") -> {
        parseColorRGBStyle(this, defaultValue)
    }

    startsWith("#") -> {
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


