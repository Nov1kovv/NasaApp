package com.example.nasaapp.domain.datasourse

import com.example.nasaapp.domain.model.DetailedFileInfo
import io.reactivex.Single

interface DetailNasaRemoteDataSource {
    fun getDetailedInfo(nasaId: String): Single<DetailedFileInfo>
    fun getVideoLink(nasaId: String): Single<String>
}