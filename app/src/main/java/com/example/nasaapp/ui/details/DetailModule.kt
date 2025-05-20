package com.example.nasaapp.ui.details

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.remote.DetailNasaRemoteDataSourceImpl
import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DetailModule {

    @Provides
    @Singleton
    fun provideDetailedDtoToDomainMapper(): DetailedDtoToDomainMapper = DetailedDtoToDomainMapper()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://images-api.nasa.gov")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NasaApiService {
        return retrofit.create(NasaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailNasaRemoteDataSource(apiService: NasaApiService, dtoToDomainMapper: DtoToDomainMapper
    ): DetailNasaRemoteDataSource = DetailNasaRemoteDataSourceImpl(apiService, dtoToDomainMapper)


//    @Provides
//    @Singleton
//    fun provideNasaRepository(remoteDataSource: DetailNasaRemoteDataSource): NasaRepository = NasaRepositoryImpl(remoteDataSource)
}