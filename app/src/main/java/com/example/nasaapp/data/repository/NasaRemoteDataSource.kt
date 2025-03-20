package com.example.nasaapp.data.repository

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.domain.model.SearchItem

class NasaRemoteDataSource(private val apiService: NasaApiService, private val dtoToDomainMapper: DtoToDomainMapper) {
    suspend fun searchImages(query: String, mediaType: String, page: Int): List<SearchItem>  {
        return dtoToDomainMapper.map(apiService.searchImages(query, mediaType,page))
    }
}