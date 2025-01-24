package com.example.nasaapp.data.value_object


import com.google.gson.annotations.SerializedName

data class Link(
    val href: String,
    val rel: String,
    val render: String
)