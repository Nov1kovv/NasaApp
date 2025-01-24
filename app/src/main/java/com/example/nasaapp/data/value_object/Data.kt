package com.example.nasaapp.data.value_object


import com.google.gson.annotations.SerializedName

data class Data(
    val album: List<String>,
    val center: String,
    @SerializedName("date_created")
    val dateCreated: String,
    val description: String,
    @SerializedName("description_508")
    val description508: String,
    val keywords: List<String>,
    val location: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("nasa_id")
    val nasaId: String,
    @SerializedName("secondary_creator")
    val secondaryCreator: String,
    val title: String
)