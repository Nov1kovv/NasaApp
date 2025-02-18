package com.example.nasaapp.data.api

import com.example.nasaapp.data.model.NasaResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApiService {
    @GET("search")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("media_type") mediaType: String = "image"
    ): NasaResponseDto

    @GET("metadata/{nasaId}")
    suspend fun getImageDetails(
        @Path("nasaId") nasaId: String
    ): NasaResponseDto
}
