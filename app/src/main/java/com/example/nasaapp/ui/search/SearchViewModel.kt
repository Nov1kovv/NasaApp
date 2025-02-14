package com.example.nasaapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.usecase.SearchImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val searchImagesUseCase: SearchImagesUseCase) : ViewModel() {

    val searchResults = MutableLiveData<List<SearchItem>>()

    fun fetchImageDetails(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = searchImagesUseCase.execute(query)
            searchResults.postValue(result)
        }
    }
}