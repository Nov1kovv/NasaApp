package com.example.nasaapp.domain.usecase

import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.Single

class SearchImagesUseCase(private val repository: NasaRepository) {
    suspend fun execute(query: String, mediaType: String? = null): List<SearchItem> {
        return repository.searchImages(query, mediaType)
    }
}