package com.example.nasaapp.data.repository

import android.util.Log
import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.domain.model.DetailedFileInfo

class DetailNasaRemoteDataSource(private val apiService: NasaApiService) {
    suspend fun getDetailedInfo(nasaId: String): DetailedFileInfo {
        val metadata = apiService.getMetadataUrl(nasaId)
        val detailedInfoDto = apiService.getResourceInfo(metadata.location)
        return DetailedDtoToDomainMapper.map(detailedInfoDto)
    }
    suspend fun getVideoLink(nasaId: String): String {
        val assetCollection = apiService.getVideoLink("https://images-api.nasa.gov/asset/$nasaId")
        Log.d("DetailNasaRemoteDataSource", "Fetched video asset collection: $assetCollection")
        return assetCollection.items.firstOrNull()?.href ?: ""
    }
}