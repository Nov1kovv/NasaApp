package com.example.nasaapp.data.value_object


import com.google.gson.annotations.SerializedName

data class Collection(
    val href: String,
    val items: List<Item>,
    val links: List<LinkX>,
    val metadata: Metadata,
    val version: String
)