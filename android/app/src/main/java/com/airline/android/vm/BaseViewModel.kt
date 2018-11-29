package com.airline.android.vm

import androidx.lifecycle.ViewModel
import com.airline.android.di.DaggerViewModelInjector
import com.airline.android.di.NetModule
import com.airline.android.di.ViewModelInjector

/**
 * Super class for other view models.<br>
 * Injects all dependencies via [ViewModelInjector]
 */
open class BaseViewModel : ViewModel() {
    /**
     * Injector(instantiated via generated DaggerViewModelInjector class)
     */
    private val injector: ViewModelInjector =
        DaggerViewModelInjector.builder()
            .netModule(NetModule())
            .build()

    init {
        inject()
    }

    /**
     * There are all specific types must be chosen
     */
    private fun inject() {
        when (this) {
            // smart type case
            is HomeViewModel -> injector.inject(this)
        }
    }
}