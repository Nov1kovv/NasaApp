package com.example.nasaapp.ui.details

import com.example.nasaapp.ui.search.SearchFragment
import com.example.nasaapp.ui.search.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface DetailComponent {
    fun inject(detailFragment: DetailFragment)

    fun inject(searchFragment: SearchFragment)

    @Component.Factory
    interface DetailFactory {
        fun create(): DetailComponent
    }
}