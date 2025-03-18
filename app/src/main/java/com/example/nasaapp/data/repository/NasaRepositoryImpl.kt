package com.example.nasaapp.data.repository

import com.example.nasaapp.data.model.detailed.AssetCollectionDto
import com.example.nasaapp.data.model.search.CollectionDto
import com.example.nasaapp.data.model.search.NasaResponseDto
import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository

class NasaRepositoryImpl(
    private val remoteDataSource: NasaRemoteDataSource,
    private val detailNasaRemoteDataSource: DetailNasaRemoteDataSource
) : NasaRepository {
    override suspend fun searchImages(query: String, mediaType: String?): List<SearchItem> {
        val response = remoteDataSource.searchImages(query, mediaType.toString())
        return response
    }

    override suspend fun getDetailedInfo(nasaId: String): DetailedFileInfo {
        return detailNasaRemoteDataSource.getDetailedInfo(nasaId)
    }

    override suspend fun getVideoLink(nasaId: String): String {
        return detailNasaRemoteDataSource.getVideoLink(nasaId)
    }
}