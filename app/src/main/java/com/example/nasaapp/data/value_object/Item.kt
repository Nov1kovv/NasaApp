package com.example.nasaapp.data.value_object

//TODO: Добавить конвертацию в Domain models
data class Item(
    val `data`: List<Data>,
    val href: String,
    val links: List<Link>
)