package com.example.nasaapp.data.value_object

import com.google.gson.annotations.SerializedName

//TODO: Добавить конвертацию в Domain models
data class Collection(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("links")
    val links: List<LinkX>,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("version")
    val version: String
)