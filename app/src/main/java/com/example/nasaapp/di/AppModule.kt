package com.example.nasaapp.di

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.repository.DetailNasaRemoteDataSource
import com.example.nasaapp.data.repository.NasaRemoteDataSource
import com.example.nasaapp.data.repository.NasaRepositoryImpl
import com.example.nasaapp.domain.repository.NasaRepository
import com.example.nasaapp.domain.usecase.GetDetailedInfoUseCase
import com.example.nasaapp.domain.usecase.SearchImagesUseCase
import com.example.nasaapp.ui.search.SearchViewModel
import com.example.nasaapp.ui.search.details.DetailViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    single {
        Retrofit.Builder()
            .baseUrl("https://images-api.nasa.gov")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    single { get<Retrofit>().create(NasaApiService::class.java) }

    single { DtoToDomainMapper }

    single { NasaRemoteDataSource(get(),get()) }

    single { DetailNasaRemoteDataSource(get())}

    single<NasaRepository> {NasaRepositoryImpl(get(), get()) }

    single { SearchImagesUseCase(get()) }

    single { GetDetailedInfoUseCase(get()) }

    viewModel { SearchViewModel(get()) }

    viewModel { DetailViewModel(get()) }
}