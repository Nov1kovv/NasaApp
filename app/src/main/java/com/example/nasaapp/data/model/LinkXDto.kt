package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName

data class LinkXDto(
    @SerializedName("href")
    val href: String,
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("rel")
    val rel: String
)