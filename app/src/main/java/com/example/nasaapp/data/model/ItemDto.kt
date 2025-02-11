package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName

//TODO: Добавить конвертацию в Domain models
data class ItemDto(
    @SerializedName("data")
    val `data`: List<DataDto>,
    @SerializedName("href")
    val href: String,
    @SerializedName("links")
    val links: List<LinkDto>
)