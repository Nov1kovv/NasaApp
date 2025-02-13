package com.example.nasaapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchItem(
    val description: String,
    val imageUrl: String,
    val date: String
):Parcelable
