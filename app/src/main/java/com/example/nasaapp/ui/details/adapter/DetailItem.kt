package com.example.nasaapp.ui.details.adapter

sealed class DetailItem {
    data class Photo(val imageUrl: String) : DetailItem()
    data class Video(val videoUrl: String) : DetailItem()
    data class TextItem(val description: String) : DetailItem()
    data class DownloadButtonItem(val url: String) : DetailItem()
    data class ShareButtonItem(val url: String) : DetailItem()
}