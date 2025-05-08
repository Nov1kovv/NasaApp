package com.example.nasaapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val repository: NasaRepository) : ViewModel() {
    val searchResult = MutableLiveData<List<SearchItem>>()
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ items ->
                searchResult.value = items
            }, { error ->
                errorMessage.value = error.message
            })
        compositeDisposable.add(disposable)
    }


        override fun onCleared() {
            super.onCleared()
            compositeDisposable.clear()
        }
    }
