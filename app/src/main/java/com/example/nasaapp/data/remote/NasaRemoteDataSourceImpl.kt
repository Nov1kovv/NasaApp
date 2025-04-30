package com.example.nasaapp.data.remote

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.domain.datasourse.NasaRemoteDataSource
import com.example.nasaapp.domain.model.SearchItem

class NasaRemoteDataSourceImpl(private val apiService: NasaApiService, private val dtoToDomainMapper: DtoToDomainMapper): NasaRemoteDataSource {
    override suspend fun searchImages(query: String, mediaType: String, page: Int): List<SearchItem>  {
        return dtoToDomainMapper.map(apiService.searchImages(query, mediaType,page))
    }
}