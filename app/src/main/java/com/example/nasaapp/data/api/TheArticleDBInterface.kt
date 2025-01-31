package com.example.nasaapp.data.api

import com.example.nasaapp.data.value_object.NasaResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface TheArticleDBInterface {
    @GET("search")
    fun searchImages(
        @Query("q") query: String, @Query("media_type") mediaType: String = "image"
    ): Single<NasaResponse>
}