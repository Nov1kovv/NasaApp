package com.example.nasaapp.data.model.search

import com.google.gson.annotations.SerializedName

data class LinkDto(
    @SerializedName("href") val href: String,
    @SerializedName("rel") val rel: String,
    @SerializedName("render") val render: String
)