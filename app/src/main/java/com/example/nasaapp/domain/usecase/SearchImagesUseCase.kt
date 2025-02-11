package com.example.nasaapp.domain.usecase

import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
//Пакет который реализует бизнес логику, используется во ViewModel
class SearchImagesUseCase(private val repository: NasaRepository) {
    suspend fun execute(query: String): List<SearchItem> {
        return repository.searchImages(query)
    }
}