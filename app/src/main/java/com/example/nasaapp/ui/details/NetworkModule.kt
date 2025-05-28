package com.example.nasaapp.ui.details

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.remote.DetailNasaRemoteDataSourceImpl
import com.example.nasaapp.data.repository.NasaRepositoryImpl
import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import com.example.nasaapp.domain.repository.NasaRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
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
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
    fun provideApiService(retrofit: Retrofit): NasaApiService {
        return retrofit.create(NasaApiService::class.java)
    }

    //@Named нужен, когда у тебя есть две или более зависимости одного и того же типа. Dagger не сможет понять, какую именно нужно инжектить
//    @Provides
//    @Named("spacex")
//    fun provideSpacexRetrofit(): Retrofit {
//        return retrofit.create(NasaApiService::class.java)
//    }

    @Provides
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
    fun provideDetailNasaRemoteDataSource(
        apiService: NasaApiService, dtoToDomainMapper: DtoToDomainMapper
    ): DetailNasaRemoteDataSource = DetailNasaRemoteDataSourceImpl(apiService, dtoToDomainMapper)


//    @Binds
//    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
//    fun provideNasaRepository(remoteDataSource: DetailNasaRemoteDataSource): NasaRepository =
//        NasaRepositoryImpl(remoteDataSource)
}

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNasaRepository(impl: NasaRepositoryImpl): NasaRepository
}