package com.example.nasaapp.ui.details

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface DetailComponent {
    fun inject(detailFragment: DetailFragment)

    @Component.Factory
    interface DetailFactory {
        fun create(): DetailComponent
    }
}