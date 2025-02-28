package com.example.nasaapp.data.api

import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.data.model.detailed.MetaDataLinkDto
import com.example.nasaapp.data.model.search.NasaResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface NasaApiService {
    @GET("search")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("media_type") mediaType: String 
    ): NasaResponseDto

    @GET("metadata/{nasaId}")
    suspend fun getMetadataUrl(@Path("nasaId") nasaId: String): MetaDataLinkDto

    @GET
    suspend fun getResourceInfo(@Url url: String): ItemDetailedInfoDto
}
