package com.example.nasaapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.value_object.NasaResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val apiService: TheArticleDBInterface) : ViewModel() {
    val nasaResponse = MutableLiveData<NasaResponse>()
    private val compositeDisposable = CompositeDisposable()

    fun searchImages(query: String) {
        val disposable = apiService.searchImages(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                nasaResponse.postValue(response)
            }, { error ->
                // Логирование ошибки
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}