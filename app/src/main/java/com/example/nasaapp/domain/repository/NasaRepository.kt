package com.example.nasaapp.domain.repository

import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.data.model.detailed.MetaDataLinkDto
import com.example.nasaapp.domain.model.DetailedItem
import com.example.nasaapp.domain.model.SearchItem
import io.reactivex.Single

interface NasaRepository {
    fun searchImages(query: String, mediaType: String? = null, page: Int = 1): Single<List<SearchItem>>
    fun getVideoLink(nasaId: String): Single<String>
    fun getMetadataUrl(nasaId: String): Single<MetaDataLinkDto> // TODO: репозиторий всегда возвращает domain сущности
    fun getResourceInfo(url: String): Single<ItemDetailedInfoDto>
}