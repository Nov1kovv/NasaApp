package com.example.nasaapp.data.remote

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import com.example.nasaapp.domain.model.DetailedItem
import io.reactivex.Single

class DetailNasaRemoteDataSourceImpl(private val apiService: NasaApiService):
    DetailNasaRemoteDataSource {

    // TODO: вынести во ViewModel, количество методов datasource должно соответсовать количеству методов ApiService. Dto должны мапиться в domain сущности или в примитивные типы
    override fun getDetailedInfo(nasaId: String): Single<DetailedItem> {
        return apiService.getMetadataUrl(nasaId)
            .flatMap { metaData ->
                apiService.getResourceInfo(metaData.location)
                    .flatMap { detailedInfoDto ->
                        getVideoLink(nasaId)
                            .map { videoUrl ->
                                DetailedDtoToDomainMapper.map(detailedInfoDto, videoUrl)
                                }
                    }
            }
    }
    override fun getVideoLink(nasaId: String): Single<String> {
        return apiService.getVideoLink("https://images-api.nasa.gov/asset/$nasaId")
            .map { assetCollection ->
                assetCollection.collection.items.firstOrNull { it.href.endsWith(".mp4") }?.href.orEmpty()
            }
    }
}