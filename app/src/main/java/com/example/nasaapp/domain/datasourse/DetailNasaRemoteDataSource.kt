package com.example.nasaapp.domain.datasourse

import com.example.nasaapp.data.model.detailed.AssetDto
import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.data.model.detailed.MetaDataLinkDto
import com.example.nasaapp.data.model.search.NasaResponseDto
import com.example.nasaapp.domain.model.DetailedItem
import com.example.nasaapp.domain.model.SearchItem
import io.reactivex.Single

//2 метода, а должно быть 4 как в ApiSevice с такими же названиями
interface DetailNasaRemoteDataSource {
    fun searchImages(query: String, mediaType: String, page: Int): Single<List<SearchItem>>
    fun getVideoLink(url: String): Single<AssetDto>
    fun getMetadataUrl(nasaId: String): Single<MetaDataLinkDto>
    fun getResourceInfo(url: String): Single<ItemDetailedInfoDto>
}