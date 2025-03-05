package com.example.nasaapp.data.model.detailed

import com.google.gson.annotations.SerializedName


data class AssetCollection(
    @SerializedName("collection")
    val collection: AssetCollectionDto
)