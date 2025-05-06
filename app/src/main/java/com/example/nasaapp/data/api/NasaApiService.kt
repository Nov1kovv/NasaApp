package com.example.nasaapp.data.api

import com.example.nasaapp.data.model.detailed.AssetDto
import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.data.model.detailed.MetaDataLinkDto
import com.example.nasaapp.data.model.search.NasaResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface NasaApiService {
    @GET("search")
    fun searchImages(
        @Query("q") query: String,
        @Query("media_type") mediaType: String,
        @Query("page") page: Int
    ): Single<NasaResponseDto>

    @GET("metadata/{nasaId}")
     fun getMetadataUrl(@Path("nasaId") nasaId: String): Single<MetaDataLinkDto>

    @GET
     fun getResourceInfo(@Url url: String): Single<ItemDetailedInfoDto>

    @GET
     fun getVideoLink(@Url url: String): Single<AssetDto>
}
