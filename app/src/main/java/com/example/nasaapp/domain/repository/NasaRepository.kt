package com.example.nasaapp.domain.repository

import com.example.nasaapp.data.model.detailed.AssetCollectionDto
import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.model.SearchItem

interface NasaRepository {
    suspend fun searchImages(query: String, mediaType: String? = null,page: Int = 1): List<SearchItem>
    suspend fun getDetailedInfo(nasaId: String): DetailedFileInfo
    suspend fun getVideoLink(nasaId: String): String
}