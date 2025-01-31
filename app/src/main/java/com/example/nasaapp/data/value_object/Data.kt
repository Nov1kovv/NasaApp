package com.example.nasaapp.data.value_object


import com.google.gson.annotations.SerializedName

//TODO: Добавить конвертацию в Domain models
data class Data(
    @SerializedName("album")
    val album: List<String>,
    @SerializedName("center")
    val center: String,
    @SerializedName("date_created")
    val dateCreated: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("description_508")
    val description508: String,
    @SerializedName("keywords")
    val keywords: List<String>,
    @SerializedName("location")
    val location: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("nasa_id")
    val nasaId: String,
    @SerializedName("secondary_creator")
    val secondaryCreator: String,
    @SerializedName("title")
    val title: String
)