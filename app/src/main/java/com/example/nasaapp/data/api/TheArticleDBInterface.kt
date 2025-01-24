package com.example.nasaapp.data.api

import com.example.nasaapp.data.value_object.ArticleDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

//https://images-api.nasa.gov/search?q=moon&media_type=image

interface TheArticleDBInterface {
    @GET("nasa/{nasa_id}")
    fun getNasaId(@Path("nasa_id") id: String) : Single<ArticleDetails>
}