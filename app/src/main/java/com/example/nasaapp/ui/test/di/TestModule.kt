package com.example.nasaapp.ui.test.di

import com.example.nasaapp.ui.test.Contract
import com.example.nasaapp.ui.test.ContractImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class TestModule {

    @Binds
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
    abstract fun provideContract(impl: ContractImpl): Contract
}