package com.example.nasaapp.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://images-api.nasa.gov"
const val IMAGE_BASE_URL = "https://images-assets.nasa.gov/image/PIA12235/PIA12235~thumb"
object TheArticleDBClient {

    fun getClient(): TheArticleDBInterface{
        val requestInterceptor = Interceptor {
            val url = it.request()
                .url
                .newBuilder()
                .build()

            val request = it.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor it.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheArticleDBInterface::class.java)
    }
}