package org.ligi.kaxt.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
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
