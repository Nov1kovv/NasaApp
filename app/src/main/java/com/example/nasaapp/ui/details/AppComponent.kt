package com.example.nasaapp.ui.details

import com.example.nasaapp.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
@Component(modules = [NetworkModule::class, DetailedModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(detailFragment: DetailFragment)
    fun inject(searchFragment: SearchFragment)

    @Component.Factory
    interface DetailFactory {
        fun create(): AppComponent
    }
}