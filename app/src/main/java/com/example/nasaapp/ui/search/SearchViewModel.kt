package com.example.nasaapp.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.usecase.SearchImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val searchImagesUseCase: SearchImagesUseCase) : ViewModel() {

    val searchResults = MutableLiveData<List<SearchItem>>()

    fun fetchImageDetails(query: String, mediaType: String = "image") {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = searchImagesUseCase.execute(query, mediaType)
                searchResults.postValue(result)
            } catch (e: Exception) {
                searchResults.postValue(emptyList())
                Log.e("SearchViewModel", "Ошибка при поиске данных", e)
            }
        }
    }

    fun search(mediaType: String) {
        fetchImageDetails("nasa", mediaType)
    }
}