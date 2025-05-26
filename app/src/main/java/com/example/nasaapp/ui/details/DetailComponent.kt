package com.example.nasaapp.ui.details

import com.example.nasaapp.ui.search.SearchFragment
import dagger.Component
import dagger.assisted.Assisted
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DetailedModule::class])
interface DetailComponent {

    fun inject(detailFragment: DetailFragment)
    fun inject(searchFragment: SearchFragment)

    @Component.Factory
    interface DetailFactory {
        fun create(): DetailComponent
    }
}