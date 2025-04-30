package com.example.nasaapp.domain.usecase

import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.Single

class GetVideoLinkUseCase(private val repository: NasaRepository) {

     fun execute(nasaId: String): Single<String> {
        return repository.getVideoLink(nasaId)
    }
}