package org.ligi.kaxt.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

fun <T> LiveData<T>.nonNull() = NonNullMediatorLiveData<T>().apply {
    addSource(this@nonNull) { potentiallyNull -> potentiallyNull?.let { value = it } }
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    observe(owner, androidx.lifecycle.Observer {
        it?.let(observer)
    })
}

