package com.example.nasaapp.data.remote

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.data.model.detailed.AssetDto
import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.data.model.detailed.MetaDataLinkDto
import com.example.nasaapp.domain.datasourse.DetailNasaRemoteDataSource
import com.example.nasaapp.domain.model.SearchItem
import io.reactivex.Single

class DetailNasaRemoteDataSourceImpl(private val apiService: NasaApiService):
    DetailNasaRemoteDataSource {

    // TODO: вынести во ViewModel, количество методов datasource должно соответсовать количеству методов ApiService. Dto должны мапиться в domain сущности или в примитивные типы
//    override fun searchImages(query: String, mediaType: String, page: Int): Single<List<SearchItem>> {
//        return apiService.searchImages(query, mediaType,page)
//            .map{dtoToDomainMapper.map(it)}
//    }
        override fun getMetadataUrl(nasaId: String): Single<MetaDataLinkDto> =
        apiService.getMetadataUrl(nasaId)

    override fun getResourceInfo(url: String): Single<ItemDetailedInfoDto> =
        apiService.getResourceInfo(url)

    override fun getVideoLink(url: String): Single<AssetDto> =
        apiService.getVideoLink(url)
    }
