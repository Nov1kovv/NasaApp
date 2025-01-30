package com.example.nasaapp.ui.single_article_details

import androidx.lifecycle.LiveData
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.repository.ArticleDetailsNetworkDataSource
import com.example.nasaapp.data.repository.NetworkState
import com.example.nasaapp.data.value_object.NasaResponse
import io.reactivex.disposables.CompositeDisposable

class ArticleDetailsRepository (private val apiServise : TheArticleDBInterface) {
    lateinit var articleDetailsNetworkDataSource: ArticleDetailsNetworkDataSource
    fun fetchSingleArticleDetails (compositeDisposable: CompositeDisposable, nasaId: String) : LiveData<NasaResponse>{
        articleDetailsNetworkDataSource = ArticleDetailsNetworkDataSource (apiServise, compositeDisposable)
        articleDetailsNetworkDataSource.fetchImageDetails(nasaId)

        return articleDetailsNetworkDataSource.downloadedArticleResponse
    }
    fun getArticleDetailsNetworkState(): LiveData<NetworkState>{
        return articleDetailsNetworkDataSource.networkState
    }
}
