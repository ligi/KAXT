package org.ligi.kaxt


inline fun <T> T.applyIf(predicate: Boolean, block: T.() -> Unit): T {
    if (predicate) block()
    return this
}