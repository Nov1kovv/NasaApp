package com.example.nasaapp.data.repository

import com.example.nasaapp.data.remote.DetailNasaRemoteDataSourceImpl
import com.example.nasaapp.data.remote.NasaRemoteDataSourceImpl
import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import com.example.nasaapp.domain.datasourse.NasaRemoteDataSource
import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.Single

class NasaRepositoryImpl(
    private val remoteDataSource: NasaRemoteDataSource,
    private val detailNasaRemoteDataSource: DetailNasaRemoteDataSource
) : NasaRepository {
    override suspend fun searchImages(
        query: String,
        mediaType: String?,
        page: Int
    ): List<SearchItem> {
        return remoteDataSource.searchImages(query, mediaType ?: "image,video,audio", page)
    }

    override fun getDetailedInfo(nasaId: String): Single<DetailedFileInfo> {
        return detailNasaRemoteDataSource.getDetailedInfo(nasaId)
    }

    override fun getVideoLink(nasaId: String): Single<String> {
        return detailNasaRemoteDataSource.getVideoLink(nasaId)
    }
}