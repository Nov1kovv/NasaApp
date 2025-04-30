package com.example.nasaapp.domain.repository

import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.model.SearchItem
import io.reactivex.Single

interface NasaRepository {
    suspend fun searchImages(query: String, mediaType: String? = null, page: Int = 1): List<SearchItem>
    fun getDetailedInfo(nasaId: String): Single<DetailedFileInfo>
    fun getVideoLink(nasaId: String): Single<String>
}