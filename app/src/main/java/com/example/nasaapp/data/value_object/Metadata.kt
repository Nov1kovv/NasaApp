package com.example.nasaapp.data.value_object


import com.google.gson.annotations.SerializedName

//TODO: Добавить конвертацию в Domain models
data class Metadata(
    @SerializedName("total_hits")
    val totalHits: Int
)