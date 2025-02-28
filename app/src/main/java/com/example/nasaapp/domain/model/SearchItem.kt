package com.example.nasaapp.domain.model

data class SearchItem(
    val description: String,
    val imageUrl: String,
    val date: String,
    val nasaId: String,
    val isVideo: Boolean
)
