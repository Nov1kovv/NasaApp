package com.example.nasaapp.data.value_object

data class Item(
    val `data`: List<Data>,
    val href: String,
    val links: List<Link>
)