package com.example.nasaapp.data.repository

import com.example.nasaapp.data.api.NasaApiService
import com.example.nasaapp.data.mapper.DtoToDomainMapper
import com.example.nasaapp.domain.model.SearchItem
import okhttp3.OkHttpClient
import okhttp3.Request

class NasaRemoteDataSource(private val apiService: NasaApiService, private val dtoToDomainMapper: DtoToDomainMapper) {

    fun getImageFileDetails(imageUrl: String): Pair<String, String> {
        val client = OkHttpClient()
        val request = Request.Builder().url(imageUrl).build()
        val response = client.newCall(request).execute()

        val contentLength = response.header("Content-Length", "Unknown")
        val contentType = response.header("Content-Type", "Unknown")

        return Pair(contentLength ?: "Unknown", contentType ?: "Unknown")
    }

    suspend fun searchImages(query: String): List<SearchItem>  {
        return dtoToDomainMapper.map(apiService.searchImages(query))
    }
}