package com.example.nasaapp.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nasaapp.data.paging.NasaPagingSource
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import com.example.nasaapp.domain.usecase.SearchImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: NasaRepository) : ViewModel() {
    fun searchImages(query: String, mediaType: String): Flow<PagingData<SearchItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { NasaPagingSource(repository, query, mediaType) }
        ).flow.cachedIn(viewModelScope)
    }
}
