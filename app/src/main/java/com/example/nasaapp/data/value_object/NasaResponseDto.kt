package com.example.nasaapp.data.value_object

import com.google.gson.annotations.SerializedName


//TODO: Добавить конвертацию в Domain models
data class NasaResponseDto(
    @SerializedName("collection")
    val collection: Collection
)