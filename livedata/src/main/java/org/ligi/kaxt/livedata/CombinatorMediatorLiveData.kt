package org.ligi.kaxt.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class CombinatorMediatorLiveData<T>(ins: List<MutableLiveData<out Any>>, function: () -> T) : MediatorLiveData<T>() {
    init {
        ins.forEach {
            addSource(it) { value = function() }
        }
    }
}