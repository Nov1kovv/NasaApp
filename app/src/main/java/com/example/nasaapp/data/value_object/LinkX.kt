package com.example.nasaapp.data.value_object


import com.google.gson.annotations.SerializedName

data class LinkX(
    val href: String,
    val prompt: String,
    val rel: String
)