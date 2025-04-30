package com.example.nasaapp.domain.datasourse

import com.example.nasaapp.domain.model.SearchItem

interface NasaRemoteDataSource {
    suspend fun searchImages(query: String, mediaType: String, page: Int): List<SearchItem>
}