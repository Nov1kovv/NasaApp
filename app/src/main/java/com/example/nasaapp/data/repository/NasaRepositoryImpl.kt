package com.example.nasaapp.data.repository

import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository

class NasaRepositoryImpl(private val remoteDataSource: NasaRemoteDataSource) : NasaRepository {
    override suspend fun searchImages(query: String): List<SearchItem> {
        val response = remoteDataSource.searchImages(query)
        return response
    }
}