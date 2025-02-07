package com.example.nasaapp.data.mapper

import com.example.nasaapp.data.model.NasaResponseDto
import com.example.nasaapp.domain.model.SearchItem
//Преобразует DTO из API в доменную модель
//Связь: Используется в репозитории
object NasaResponseMapper {
    fun map(dto: NasaResponseDto): List<SearchItem> {
        return dto.collection.items.map { item ->
            val data = item.data.firstOrNull()
            SearchItem(
                name = data?.title.orEmpty(),
                imageUrl = item.links.firstOrNull()?.href.orEmpty(),
                date = data?.dateCreated.orEmpty()
            )
        }
    }
}