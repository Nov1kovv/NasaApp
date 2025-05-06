package com.example.nasaapp.data.remote

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.domain.datasourse.NasaRemoteDataSource
import com.example.nasaapp.domain.model.SearchItem
import io.reactivex.Single

class NasaRemoteDataSourceImpl(private val apiService: NasaApiService, private val dtoToDomainMapper: DtoToDomainMapper): NasaRemoteDataSource {
    override fun searchImages(query: String, mediaType: String, page: Int): Single<List<SearchItem>> {
        return apiService.searchImages(query, mediaType,page)
            .map{dtoToDomainMapper.map(it)}
    }
}