package com.example.nasaapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _searchResult = MutableLiveData<List<SearchItem>>()
    val searchResult: LiveData<List<SearchItem>> = _searchResult

    private val errorMessage = MutableLiveData<String?>()
    private val compositeDisposable = CompositeDisposable()

    init {
        searchImages("nasa", mediaType = "image")
    }

    fun searchImages(
        query: String,
        mediaType: String
    ) {
        val disposable = repository.searchImages(query, mediaType)
            .subscribeOn(Schedulers.io())
            .subscribe({ items ->
                _searchResult.postValue(items)
            }, { error ->
                errorMessage.postValue(error.message)
            })
        compositeDisposable.add(disposable)
    }


        override fun onCleared() {
            super.onCleared()
            compositeDisposable.clear()
        }
    }
