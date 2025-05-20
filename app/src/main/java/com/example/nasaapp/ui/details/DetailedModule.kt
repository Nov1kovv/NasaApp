package com.example.nasaapp.ui.details

import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DetailedModule {

    @Provides
    @Singleton
    fun provideDetailedDtoToDomainMapper(): DetailedDtoToDomainMapper = DetailedDtoToDomainMapper()
}