package com.example.nasaapp.data.repository

import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository

class NasaRepositoryImpl(
    private val remoteDataSource: NasaRemoteDataSource,
    private val detailNasaRemoteDataSource: DetailNasaRemoteDataSource
) : NasaRepository {
    override suspend fun searchImages(query: String): List<SearchItem> {
        val response = remoteDataSource.searchImages(query,"image,video")
        return response
//        val imageResults = remoteDataSource.searchImages(query, "image")
//        val videoResults = remoteDataSource.searchImages(query, "video")
//        return imageResults + videoResults
    }

    override suspend fun getDetailedInfo(nasaId: String): DetailedFileInfo {
        return detailNasaRemoteDataSource.getDetailedInfo(nasaId)
    }
}