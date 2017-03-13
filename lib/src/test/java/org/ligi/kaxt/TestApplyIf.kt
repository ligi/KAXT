package org.ligi.kaxt

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestApplyIf {

    data class Probe(var field: String = "old")

    @Test
    fun shouldNotApplyIfFalse() {
        assertThat(Probe().applyIf(false) { field="new" }.field).isEqualTo("old")
    }

    @Test
    fun shouldApplyIfTrue() {
        assertThat(Probe().applyIf(true) { field="new" }.field).isEqualTo("new")
    }
}
