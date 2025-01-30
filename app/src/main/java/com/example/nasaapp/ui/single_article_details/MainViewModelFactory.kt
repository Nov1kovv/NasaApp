package com.example.nasaapp.ui.single_article_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasaapp.data.api.TheArticleDBInterface

class MainViewModelFactory(private val apiService: TheArticleDBInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(apiService) as T
    }
}