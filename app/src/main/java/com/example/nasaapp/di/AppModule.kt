package com.example.nasaapp.di

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.remote.DetailNasaRemoteDataSourceImpl
import com.example.nasaapp.data.repository.NasaRepositoryImpl
import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import com.example.nasaapp.domain.repository.NasaRepository
import com.example.nasaapp.ui.search.SearchViewModel
import com.example.nasaapp.ui.details.DetailViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(NasaApiService::class.java) }

    single { DtoToDomainMapper() }

    single<DetailNasaRemoteDataSource> { DetailNasaRemoteDataSourceImpl(get(),get()) }

    single<NasaRepository> {NasaRepositoryImpl(get()) }

    viewModel { SearchViewModel(get()) }

}