package com.example.nasaapp.di

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.repository.NasaRemoteDataSource
import com.example.nasaapp.data.repository.NasaRepositoryImpl
import com.example.nasaapp.domain.repository.NasaRepository
import com.example.nasaapp.domain.usecase.SearchImagesUseCase
import com.example.nasaapp.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder().baseUrl("https://images-api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single { get<Retrofit>().create(NasaApiService::class.java) }

    single { DtoToDomainMapper }

    single { NasaRemoteDataSource(get(), get()) }

    single<NasaRepository> { NasaRepositoryImpl(get()) }

    single { SearchImagesUseCase(get()) }

    viewModel { SearchViewModel(get()) }

}