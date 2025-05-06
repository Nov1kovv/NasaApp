package com.example.nasaapp.domain.datasourse

import com.example.nasaapp.domain.model.DetailedItem
import io.reactivex.Single

interface DetailNasaRemoteDataSource {
    fun getDetailedInfo(nasaId: String): Single<DetailedItem>
    fun getVideoLink(nasaId: String): Single<String>
}