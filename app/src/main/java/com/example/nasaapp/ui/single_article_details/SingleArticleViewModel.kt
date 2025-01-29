package com.example.nasaapp.ui.single_article_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.data.repository.NetworkState
import com.example.nasaapp.data.value_object.ArticleDetails
import io.reactivex.disposables.CompositeDisposable

class SingleArticleViewModel(private val articleRepository : ArticleDetailsRepository, nasaId : String) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val articleDetails : LiveData<ArticleDetails> by lazy {
        articleRepository.fetchSingleArticleDetails(compositeDisposable, nasaId)
    }
    val networkState : LiveData<NetworkState> by lazy {
        articleRepository.getArticleDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}