package com.example.nasaapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nasaapp.data.paging.NasaPagingSource
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val repository: NasaRepository) : ViewModel() {
    private var lastQuery: String = ""

    fun searchImages(query: String, mediaType: String): Flow<PagingData<SearchItem>> {
        lastQuery = query
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { NasaPagingSource(repository, query, mediaType) }
        ).flow.cachedIn(viewModelScope)
    }
    fun getLastQuery(): String = lastQuery
}
//r