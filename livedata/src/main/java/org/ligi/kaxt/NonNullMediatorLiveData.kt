package org.ligi.kaxt

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData


class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

fun <T> LiveData<T>.nonNull() = NonNullMediatorLiveData<T>().apply {
    addSource(this) { potentiallyNull -> potentiallyNull?.let { value = it } }
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    observe(owner, android.arch.lifecycle.Observer {
        it?.let(observer)
    })
}

