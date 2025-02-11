package com.example.nasaapp.data.model


import com.google.gson.annotations.SerializedName

//TODO: Добавить конвертацию в Domain models
data class MetadataDto(
    @SerializedName("total_hits")
    val totalHits: Int
)