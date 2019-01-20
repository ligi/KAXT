package org.ligi.kaxt.livedata

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData

class CombinatorMediatorLiveData<T>(ins: List<MutableLiveData<out Any>>, function: () -> T) : MediatorLiveData<T>() {
    init {
        ins.forEach {
            addSource(it) { value = function() }
        }
    }
}