package com.example.nasaapp.domain.repository

import com.example.nasaapp.domain.model.DetailedItem
import com.example.nasaapp.domain.model.SearchItem
import io.reactivex.Single

interface NasaRepository {
    fun searchImages(query: String, mediaType: String? = null, page: Int = 1): Single<List<SearchItem>>
    fun getDetailedInfo(nasaId: String): Single<DetailedItem>
    fun getVideoLink(nasaId: String): Single<String>
}