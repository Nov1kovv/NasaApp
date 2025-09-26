package com.example.nasaapp.domain.datasourse

import com.example.nasaapp.domain.model.SearchItem
import io.reactivex.Single

interface SearchNasaRemoteDataSource {
    fun searchImages(query: String, mediaType: String, page: Int): Single<List<SearchItem>>
}