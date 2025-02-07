package com.example.nasaapp.data.repository

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.model.NasaResponseDto

class NasaRemoteDataSource(private val apiService: NasaApiService) {
    suspend fun searchImages(query: String): NasaResponseDto {
        return apiService.searchImages(query)
    }
}