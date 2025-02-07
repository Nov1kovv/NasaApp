package com.example.nasaapp.data.model

import com.google.gson.annotations.SerializedName


//TODO: Добавить конвертацию в Domain models
data class Link(
    @SerializedName("href")
    val href: String,
    @SerializedName("rel")
    val rel: String,
    @SerializedName("render")
    val render: String
)