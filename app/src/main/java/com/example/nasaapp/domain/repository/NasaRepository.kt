package com.example.nasaapp.domain.repository

import com.example.nasaapp.domain.model.SearchItem

interface NasaRepository {
    suspend fun searchImages(query: String): List<SearchItem>
}