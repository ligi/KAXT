package org.ligi.kaxt.livedata

import androidx.lifecycle.MutableLiveData
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TheNonNullMediator : LiveDataTest() {

    @Test
    fun shouldNotPassNullButValue() {
        val tested = MutableLiveData<Boolean>()

        var result = false

        tested.nonNull().observe(lifecycleOwner) {
            assertThat(it).isNotNull()
            result = it
        }

        tested.postValue(true)
        tested.postValue(null)

        assertThat(result).isTrue()
    }

}
