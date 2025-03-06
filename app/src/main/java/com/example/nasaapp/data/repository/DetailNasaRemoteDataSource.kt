package com.example.nasaapp.data.repository

import android.util.Log
import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.domain.model.DetailedFileInfo

class DetailNasaRemoteDataSource(private val apiService: NasaApiService) {
    suspend fun getDetailedInfo(nasaId: String): DetailedFileInfo {
        val metadata = apiService.getMetadataUrl(nasaId)
        val detailedInfoDto = apiService.getResourceInfo(metadata.location)
        val videoUrl = getVideoLink(nasaId)
        return DetailedDtoToDomainMapper.map(detailedInfoDto,videoUrl)
    }
    suspend fun getVideoLink(nasaId: String): String {
        val assetCollection = apiService.getVideoLink("https://images-api.nasa.gov/asset/$nasaId")
        Log.d("DetailNasaRemoteDataSource", "Fetched video asset collection: $assetCollection")
        val videoItems = assetCollection.collection.items.filter {
            it.href.endsWith(".mp4") == true
        }

        val videoLink = videoItems?.firstOrNull()?.href ?: ""
        Log.d("DetailNasaRemoteDataSource", "Selected video link: $videoLink")

        return videoLink
    }
}