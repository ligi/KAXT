package org.ligi.kaxt

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData

class CombinatorMediatiorLiveData<T>(ins: List<MutableLiveData<out Any>>, function: () -> T) : MediatorLiveData<T>() {
    init {
        ins.forEach {
            addSource(it) { value = function() }
        }
    }
}