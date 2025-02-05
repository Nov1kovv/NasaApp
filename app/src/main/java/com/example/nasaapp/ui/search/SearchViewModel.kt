package com.example.nasaapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapp.data.repository.ArticleDetailsNetworkDataSource
import com.example.nasaapp.data.repository.NetworkState
import com.example.nasaapp.data.value_object.NasaResponse
import kotlinx.coroutines.launch

class SearchViewModel(private val networkDataSource: ArticleDetailsNetworkDataSource) : ViewModel() {

    private val _downloadedArticleResponse = MutableLiveData<NasaResponse>()
    val downloadedArticleResponse: LiveData<NasaResponse>
        get() = _downloadedArticleResponse

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    fun fetchImageDetails(query: String) {
        viewModelScope.launch {
            networkDataSource.fetchImageDetails(query)
            _downloadedArticleResponse.postValue(networkDataSource.downloadedArticleResponse.value)
        }
    }
}