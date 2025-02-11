package com.example.nasaapp.di

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.repository.NasaRemoteDataSource
import com.example.nasaapp.data.repository.NasaRepositoryImpl
import com.example.nasaapp.domain.repository.NasaRepository
import com.example.nasaapp.domain.usecase.SearchImagesUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private const val BASE_URL = "https://images-api.nasa.gov/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: NasaApiService = retrofit.create(NasaApiService::class.java)

    private val dtoToDomainMapper = DtoToDomainMapper

    private val remoteDataSource = NasaRemoteDataSource(apiService, dtoToDomainMapper)

    private val nasaRepository: NasaRepository = NasaRepositoryImpl(remoteDataSource)

    val searchImagesUseCase = SearchImagesUseCase(nasaRepository)
}
//