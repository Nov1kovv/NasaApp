package com.example.nasaapp.data.mapper

import android.util.Log
import com.example.nasaapp.data.model.search.NasaResponseDto
import com.example.nasaapp.domain.model.SearchItem
import javax.inject.Inject

class DtoToDomainMapper @Inject constructor() {
    fun map(dto: NasaResponseDto): List<SearchItem> {
        return dto.collection.items.map { item ->
            val data = item.data.firstOrNull()
            val isVideo = data?.mediaType == "video"
            Log.d("DtoToDomainMapper", "mediaType: ${data?.mediaType}, isVideo: $isVideo")
            Log.d("DtoToDomainMapper", "Image URL: ${item.links.firstOrNull()?.href}")
            SearchItem(
                nasaId = data?.nasaId.orEmpty(),
                description = data?.description.orEmpty(),
                imageUrl = item.links.firstOrNull()?.href.orEmpty(),
                date = data?.dateCreated.orEmpty(),
                isVideo = isVideo
            )
        }
    }
}