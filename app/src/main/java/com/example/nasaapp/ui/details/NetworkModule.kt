package com.example.nasaapp.ui.details

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.remote.DetailNasaRemoteDataSourceImpl
import com.example.nasaapp.data.remote.SearchNasaRemoteDataSourceImpl
import com.example.nasaapp.data.repository.NasaRepositoryImpl
import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import com.example.nasaapp.domain.datasourse.SearchNasaRemoteDataSource
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

    @Provides // Используется когда нету @Inject constructor и я самостоятельно создаю объект
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
    //Удаляется только при завершении приложения (очистка памяти)
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder() //получаю экземпляр класса Retrofit
            .baseUrl("https://images-api.nasa.gov")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides // Используется когда нету @Inject constructor и я самостоятельно создаю объект
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
    fun provideApiService(retrofit: Retrofit): NasaApiService {
        return retrofit.create(NasaApiService::class.java) //возвращаю экземпляр класса NasaApiService
    }

    //@Named нужен, когда у тебя есть две или более зависимости одного и того же типа. Dagger не сможет понять, какую именно нужно инжектить
//    @Provides
//    @Named("spacex")
//    fun provideSpacexRetrofit(): Retrofit {
//        return retrofit.create(NasaApiService::class.java)
//    }

    @Provides // Используется когда нету @Inject constructor и я самостоятельно создаю объект
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
    fun provideDetailNasaRemoteDataSource(
        apiService: NasaApiService
    ): DetailNasaRemoteDataSource = DetailNasaRemoteDataSourceImpl(apiService)
    //вызываю конструктор, передавая зависимость (apiService, dtoToDomainMapper)и возвращаю экземпляр DetailNasaRemoteDataSourceImpl

    @Provides
    @Singleton
    fun provideSearchNasaRemoteDataSource(
        apiService: NasaApiService,
        dtoToDomainMapper: DtoToDomainMapper
    ): SearchNasaRemoteDataSource = SearchNasaRemoteDataSourceImpl(apiService, dtoToDomainMapper)

    @Provides // Используется когда нету @Inject constructor и я самостоятельно создаю объект
    @Singleton // Один и тот же экземпляр этого класса будет использоваться везде, где он инжектится
    fun provideNasaRepository(detailDataSource: DetailNasaRemoteDataSource,
                              searchDataSource: SearchNasaRemoteDataSource): NasaRepository =
        NasaRepositoryImpl(detailDataSource, searchDataSource) // вызываю конструктор, передавая зависимость (remoteDataSource)и возвращаю экземпляр
}

//Чтобы использовать @Binds, его нужно создавать в отдельном абстрактном классе.
//В классе с имплементацией указывать @Inject constructor

//@Module
//abstract class RepositoryModule {
//
//    @Binds  -  Связывает интерфейс с реализацией
//    @Singleton
//    abstract fun bindNasaRepository(impl: NasaRepositoryImpl): NasaRepository - используй экземпляр NasaRepositoryImpl
//}


//"
// @Scope - этот объект должен жить столько, сколько живёт компонент, к которому он привязан
//@Retention(AnnotationRetention.RUNTIME)
//annotation class ActivityScope
//
//@ActivityScope
//@Subcomponent
//interface ActivityComponent
//
// Объекты будут жить, пока живёт активити или фрагмент
//
//Когда активити уничтожается, компонента больше нет, то объекты становятся недоступны и сборщик мусора их удаляет"
//