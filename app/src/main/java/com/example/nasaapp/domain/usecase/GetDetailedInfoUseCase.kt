package com.example.nasaapp.domain.usecase

import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.repository.NasaRepository

class GetDetailedInfoUseCase(private val repository: NasaRepository) {
    suspend fun execute(nasaId: String): DetailedFileInfo {
        return repository.getDetailedInfo(nasaId)
    }
}