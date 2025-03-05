package com.example.nasaapp.data.model.detailed

import com.google.gson.annotations.SerializedName


data class AssetCollectionDto(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<ItemDto>,
    @SerializedName("version")
    val version: String
)