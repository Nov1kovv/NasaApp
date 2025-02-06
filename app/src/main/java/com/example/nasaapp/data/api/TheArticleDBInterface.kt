package com.example.nasaapp.data.api

import com.example.nasaapp.data.value_object.NasaResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


interface TheArticleDBInterface {
    @GET("search")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("media_type") mediaType: String = "image"
    ): NasaResponseDto
}