package com.example.nasaapp.data.value_object


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("total_hits")
    val totalHits: Int
)