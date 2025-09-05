package com.example.nasaapp.ui.details.adapter

sealed class DetailItem {
    data class Photo(val imageUrl: String) : DetailItem()
    data class Video(val videoUrl: String) : DetailItem()
    data class TextItem(val text: String) : DetailItem()
}