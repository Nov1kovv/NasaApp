package com.example.nasaapp.data.value_object

import com.google.gson.annotations.SerializedName


//TODO: Добавить конвертацию в Domain models
data class LinkX(
    @SerializedName("href")
    val href: String,
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("rel")
    val rel: String
)