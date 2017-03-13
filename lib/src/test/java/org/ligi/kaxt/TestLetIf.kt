package org.ligi.kaxt

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestLetIf {

    @Test
    fun shouldNotLetIfFalse() {
        assertThat("probe".letIf(false) { toUpperCase() }).isEqualTo("probe")
    }

    @Test
    fun shouldLetIfTrue() {
        assertThat("probe".letIf(true) { toUpperCase() }).isEqualTo("PROBE")
    }

}
