package com.example.nasaapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.value_object.NasaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchViewModel(private val apiService: TheArticleDBInterface) : ViewModel() {
    val downloadedArticleResponse: MutableLiveData<NasaResponse> = MutableLiveData<NasaResponse>()

    fun fetchImageDetails(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val searchImage = apiService.searchImages(query)
            downloadedArticleResponse.postValue(searchImage)
        }
    }
}