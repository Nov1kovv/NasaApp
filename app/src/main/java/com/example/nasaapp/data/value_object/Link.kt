package com.example.nasaapp.data.value_object


//TODO: Добавить конвертацию в Domain models
data class Link(
    val href: String,
    val rel: String,
    val render: String
)