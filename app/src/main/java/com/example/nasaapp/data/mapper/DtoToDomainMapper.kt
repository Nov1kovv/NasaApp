package com.example.nasaapp.data.mapper

import com.example.nasaapp.data.model.search.NasaResponseDto
import com.example.nasaapp.domain.model.SearchItem

object DtoToDomainMapper {
    fun map(dto: NasaResponseDto): List<SearchItem> {
        return dto.collection.items.map { item ->
            val data = item.data.firstOrNull()
            SearchItem(
                nasaId = data?.nasaId.orEmpty(),
                description = data?.description.orEmpty(),
                imageUrl = item.links.firstOrNull()?.href.orEmpty(),
                date = data?.dateCreated.orEmpty()
            )
        }
    }
}