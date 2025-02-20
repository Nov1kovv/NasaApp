package com.example.nasaapp.data.repository

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.domain.model.DetailedFileInfo

class DetailNasaRemoteDataSource(private val apiService: NasaApiService) {
    suspend fun getDetailedInfo(nasaId: String): DetailedFileInfo {

        val metadata = apiService.getMetadataUrl(nasaId)
        val detailedInfoDto = apiService.getResourceInfo(metadata.location)
        return DetailedDtoToDomainMapper.map(detailedInfoDto)
    }
}