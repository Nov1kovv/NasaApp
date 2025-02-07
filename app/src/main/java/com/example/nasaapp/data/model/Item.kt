package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName

//TODO: Добавить конвертацию в Domain models
data class Item(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("href")
    val href: String,
    @SerializedName("links")
    val links: List<Link>
)