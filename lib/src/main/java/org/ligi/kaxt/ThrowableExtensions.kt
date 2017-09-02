package org.ligi.kaxt

import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.getStackTraceString(): String {
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter)
    printStackTrace(printWriter)
    return stringWriter.toString()
}