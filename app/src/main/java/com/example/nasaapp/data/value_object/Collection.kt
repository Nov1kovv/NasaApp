package com.example.nasaapp.data.value_object

//TODO: Добавить конвертацию в Domain models
data class Collection(
    val href: String,
    val items: List<Item>,
    val links: List<LinkX>,
    val metadata: Metadata,
    val version: String
)