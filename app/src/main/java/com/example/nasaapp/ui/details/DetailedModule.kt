package com.example.nasaapp.ui.details

import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DetailedModule {

    @Provides
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
    fun provideDetailedDtoToDomainMapper(): DetailedDtoToDomainMapper = DetailedDtoToDomainMapper()
}