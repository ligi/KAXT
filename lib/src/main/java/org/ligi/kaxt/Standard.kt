package org.ligi.kaxt


inline fun <T> T.letIf(predicate: Boolean, block: T.() -> T): T {
    if (predicate) return block()
    return this
}


inline fun <T> T.applyIf(predicate: Boolean, block: T.() -> Unit): T {
    if (predicate) block()
    return this
}