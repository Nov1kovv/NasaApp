package com.example.nasaapp.data.model.search

import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("data")
    val `data`: List<DataDto>,
    @SerializedName("href")
    val href: String,
    @SerializedName("links")
    val links: List<LinkDto>
)