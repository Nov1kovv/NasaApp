package com.example.nasaapp.ui.details

import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.ui.search.SearchFragment
import com.example.nasaapp.ui.test.di.ContractModule
import com.example.nasaapp.ui.test.di.ProvidesTestModule
import com.example.nasaapp.ui.test.di.TestModule
import dagger.Component
import javax.inject.Singleton

@Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
@Component(modules = [NetworkModule::class, DetailedModule::class, TestModule::class, ProvidesTestModule::class, ContractModule::class])
interface AppComponent { //точка доступа для объектов и интерфейс, который соединяет @Inject и @Module

    fun inject(detailFragment: DetailFragment)
    fun inject(searchFragment: SearchFragment)

    fun provideDetailedDtoToDomainMapper(): DetailedDtoToDomainMapper

    @Component.Factory
    interface DetailFactory {
        fun create(): AppComponent
    }
}