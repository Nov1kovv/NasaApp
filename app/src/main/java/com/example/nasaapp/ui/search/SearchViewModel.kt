package com.example.nasaapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val repository: NasaRepository) : ViewModel() {
    private val searchResult = MutableLiveData<List<SearchItem>>()
    private var lastQuery: String = "" //сохраняю последний запрос пользователя
    private val compositeDisposable = CompositeDisposable()

    fun searchImages(query: String, mediaType: String): Single<List<SearchItem>> { //Single - возвращает одно значение либо ошибку
        lastQuery = query //сохраняею текущий запрос, чтобы потом его можно было повторно использовать
        return repository.searchImages(query, mediaType)
}
    fun getLastQuery(): String = lastQuery //геттер возвращающий последний введённый запрос

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
