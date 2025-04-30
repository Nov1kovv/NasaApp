package com.example.nasaapp.domain.usecase

import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.Single

class GetDetailedInfoUseCase(private val repository: NasaRepository) {
     fun execute(nasaId: String): Single<DetailedFileInfo> {
        return repository.getDetailedInfo(nasaId)
    }
}