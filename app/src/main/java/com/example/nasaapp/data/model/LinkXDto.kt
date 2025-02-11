package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName


//TODO: Добавить конвертацию в Domain models
data class LinkXDto(
    @SerializedName("href")
    val href: String,
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("rel")
    val rel: String
)