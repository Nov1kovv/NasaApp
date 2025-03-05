package com.example.nasaapp.domain.usecase

import com.example.nasaapp.domain.repository.NasaRepository

class GetVideoLinkUseCase(private val repository: NasaRepository) {

    suspend fun execute(nasaId: String): String {
        return repository.getVideoLink(nasaId)
    }
}