package com.example.nasaapp.data.model.detailed

import com.google.gson.annotations.SerializedName


data class AssetDto(
    @SerializedName("collection")
    val collection: AssetCollectionDto
)