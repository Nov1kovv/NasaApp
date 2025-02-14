package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName

data class MetadataDto(
    @SerializedName("total_hits")
    val totalHits: Int
)