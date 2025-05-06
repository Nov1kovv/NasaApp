package com.example.nasaapp.data.repository

import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import com.example.nasaapp.domain.datasourse.NasaRemoteDataSource
import com.example.nasaapp.domain.model.DetailedItem
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.Single

class NasaRepositoryImpl(
    private val remoteDataSource: NasaRemoteDataSource,
    private val detailNasaRemoteDataSource: DetailNasaRemoteDataSource
) : NasaRepository {
    override fun searchImages(query: String, mediaType: String?, page: Int): Single<List<SearchItem>> {
        return remoteDataSource.searchImages(query, mediaType ?: "image,video,audio", page)
    }

    override fun getDetailedInfo(nasaId: String): Single<DetailedItem> {
        return detailNasaRemoteDataSource.getDetailedInfo(nasaId)
    }

    override fun getVideoLink(nasaId: String): Single<String> {
        return detailNasaRemoteDataSource.getVideoLink(nasaId)
    }
}