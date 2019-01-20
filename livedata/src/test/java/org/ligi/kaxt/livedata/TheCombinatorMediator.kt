package org.ligi.kaxt.livedata

import android.arch.lifecycle.MutableLiveData
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TheCombinatorMediator : LiveDataTest() {

    @Test
    fun shouldCombine() {
        val in1 = MutableLiveData<Int>().apply { value = 1 }
        val in2 = MutableLiveData<Int>().apply { value = 3 }

        var result = 0

        val tested = CombinatorMediatorLiveData(listOf(in1, in2)) {
            in1.value!!.plus(in2.value!!)
        }

        val nonNull = tested.nonNull()
        nonNull.observe(lifecycleOwner ) {
            assertThat(it).isNotNull()
            result = it
        }

        in1.postValue(5)

        assertThat(result).isEqualTo(8)

        in2.postValue(6)

        assertThat(result).isEqualTo(11)
    }

}
