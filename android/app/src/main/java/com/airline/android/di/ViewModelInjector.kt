package com.airline.android.di

import com.airline.android.vm.FlightsViewModel
import com.airline.android.vm.HomeViewModel
import com.airline.android.vm.RoutesViewModel
import com.airline.android.ui.LoginActivity
import com.airline.android.vm.SignUpViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component for dependency injection in various view models.
 */
@Singleton
@Component(modules = [NetModule::class])
interface ViewModelInjector {
    /**
     * Injector for [FlightsViewModel]
     */
    fun inject(flightsViewModel: FlightsViewModel)

    /**
     * Injector for [LoginActivity]
     */
    fun inject(loginActivity: LoginActivity)

    /**
     * Injector for [SignUpViewModel]
     */
    fun inject(signUpViewModel: SignUpViewModel)

    /**
     * Injector for [HomeViewModel]
     */
    fun inject(homeViewModel: HomeViewModel)

    /**
     * Injector for [RoutesViewModel]
     */
    fun inject(routesViewModel: RoutesViewModel)
}
