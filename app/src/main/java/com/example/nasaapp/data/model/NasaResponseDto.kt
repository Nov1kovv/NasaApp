package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName

// DataDto Transfer Object (DTO) для ответа NASA API
data class NasaResponseDto(
    @SerializedName("collection")
    val collection: CollectionDto
)