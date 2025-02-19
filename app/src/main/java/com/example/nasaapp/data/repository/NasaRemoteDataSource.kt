package com.example.nasaapp.data.repository

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.domain.model.SearchItem
import okhttp3.OkHttpClient
import okhttp3.Request

class NasaRemoteDataSource(private val apiService: NasaApiService, private val dtoToDomainMapper: DtoToDomainMapper) {
    suspend fun searchImages(query: String): List<SearchItem>  {
        return dtoToDomainMapper.map(apiService.searchImages(query))
    }
}