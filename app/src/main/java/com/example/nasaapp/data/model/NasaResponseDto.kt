package com.example.nasaapp.data.model

import com.example.nasaapp.data.value_object.Collection
import com.google.gson.annotations.SerializedName

// Data Transfer Object (DTO) для ответа NASA API
data class NasaResponseDto(
    @SerializedName("collection")
    val collection: Collection
)