package com.example.nasaapp.data.repository

import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.data.model.detailed.MetaDataLinkDto
import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import com.example.nasaapp.domain.datasourse.SearchNasaRemoteDataSource
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.Single

class NasaRepositoryImpl (
    private val detailNasaRemoteDataSource: DetailNasaRemoteDataSource,
    private val searchNasaRemoteDataSource: SearchNasaRemoteDataSource
) : NasaRepository {
    override fun searchImages(query: String, mediaType: String?, page: Int): Single<List<SearchItem>> {
        return searchNasaRemoteDataSource.searchImages(query, mediaType ?: "image,video,audio", page)
    }

    override fun getVideoLink(nasaId: String): Single<String> {
        return detailNasaRemoteDataSource.getVideoLink("https://images-api.nasa.gov/asset/$nasaId")
            .map { assetDto ->
                assetDto.collection.items.firstOrNull { it.href.endsWith(".mp4") }?.href.orEmpty()
            }
    }

    override fun getMetadataUrl(nasaId: String): Single<MetaDataLinkDto> {
        return detailNasaRemoteDataSource.getMetadataUrl(nasaId)
    }

    override fun getResourceInfo(url: String): Single<ItemDetailedInfoDto> {
        return detailNasaRemoteDataSource.getResourceInfo(url)
    }
}