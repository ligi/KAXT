package org.ligi.kaxt.livedata

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import io.mockk.mockk
import org.junit.Rule

open class LiveDataTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val lifecycle = LifecycleRegistry(mockk()).apply {
        handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    val lifecycleOwner = LifecycleOwner { lifecycle }


}
