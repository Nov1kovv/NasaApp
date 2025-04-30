package com.example.nasaapp.data.repository

import android.util.Log
import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.domain.model.DetailedFileInfo
import io.reactivex.Single

class DetailNasaRemoteDataSource(private val apiService: NasaApiService) {
    fun getDetailedInfo(nasaId: String): Single<DetailedFileInfo> {
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
    fun getVideoLink(nasaId: String): Single<String> {
        return apiService.getVideoLink("https://images-api.nasa.gov/asset/$nasaId")
            .map { assetCollection ->
                assetCollection.collection.items.firstOrNull { it.href.endsWith(".mp4") }?.href.orEmpty()
            }
    }
}