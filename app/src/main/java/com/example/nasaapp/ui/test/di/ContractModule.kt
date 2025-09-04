package com.example.nasaapp.ui.test.di

import com.example.nasaapp.ui.test.Contract
import com.example.nasaapp.ui.test.ContractImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ContractModule {

    @Binds
    abstract fun bindContract(impl: ContractImpl): Contract
}