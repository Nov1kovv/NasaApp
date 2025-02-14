package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName

data class NasaResponseDto(
    @SerializedName("collection")
    val collection: CollectionDto
)