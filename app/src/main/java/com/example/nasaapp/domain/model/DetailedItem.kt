package com.example.nasaapp.domain.model

data class DetailedItem(
    val fileSize: String,
    val fileFormat: String,
    val videoUrl: String = ""
)