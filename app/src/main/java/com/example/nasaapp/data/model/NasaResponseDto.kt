package com.example.nasaapp.data.model

import com.example.nasaapp.data.value_object.Collection
import com.google.gson.annotations.SerializedName


//TODO: Добавить конвертацию в Domain models
data class NasaResponseDto(
    @SerializedName("collection")
    val collection: Collection
)