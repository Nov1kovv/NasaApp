package com.example.nasaapp.domain.datasourse

import com.example.nasaapp.data.model.detailed.AssetDto
import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.data.model.detailed.MetaDataLinkDto
import com.example.nasaapp.data.model.search.NasaResponseDto
import com.example.nasaapp.domain.model.DetailedItem
import com.example.nasaapp.domain.model.SearchItem
import io.reactivex.Single

// TODO: Называется Detail, но в нем есть метод для Search
interface DetailNasaRemoteDataSource {
    // TODO: Как будто search должен быть в отдельном data source, но репозиторий будет один Nasa Repository
//    fun searchImages(query: String, mediaType: String, page: Int): Single<List<SearchItem>>


    fun getVideoLink(url: String): Single<AssetDto>
    fun getMetadataUrl(nasaId: String): Single<MetaDataLinkDto>
    fun getResourceInfo(url: String): Single<ItemDetailedInfoDto>
}

// TODO: Появится DetailLocalDataSoeace в который ты будет сохранять понравившуюся статью в локальную базу данных (Shared Preferences)
// TODO: Знат ьнаизсусть все методы по работе с API