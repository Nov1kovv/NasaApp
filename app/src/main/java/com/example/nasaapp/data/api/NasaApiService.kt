package com.example.nasaapp.data.api

import com.example.nasaapp.data.model.NasaResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

// Пакет api предназначен для взаимодействия с API NASA, используется в repository для получения данных
// Интерфейс для взаимодействия с API NASA
interface NasaApiService {
    @GET("search")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("media_type") mediaType: String = "image"
    ): NasaResponseDto
}