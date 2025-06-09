package com.example.nasaapp.ui.test.di

import com.example.nasaapp.ui.test.Contract
import com.example.nasaapp.ui.test.ContractImpl
import com.example.nasaapp.ui.test.CustomService
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProvidesTestModule {

    @Provides
    @Singleton
    fun provideCustomService() = CustomService()
}