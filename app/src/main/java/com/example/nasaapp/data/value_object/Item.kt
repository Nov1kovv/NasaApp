package com.example.nasaapp.data.value_object


import com.google.gson.annotations.SerializedName

data class Item(
    val `data`: List<Data>,
    val href: String,
    val links: List<Link>
)