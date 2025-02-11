package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName


data class CollectionDto(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<ItemDto>,
    @SerializedName("links")
    val links: List<LinkXDto>,
    @SerializedName("metadataDto")
    val metadataDto: MetadataDto,
    @SerializedName("version")
    val version: String
)