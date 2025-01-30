package com.example.nasaapp.ui.single_article_details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.value_object.NasaResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel (private val apiService: TheArticleDBInterface) : ViewModel() {
    val nasaResponse = MutableLiveData<NasaResponse>()
    private val compositeDisposable = CompositeDisposable()

    fun fetchImageDetails(query: String) {
        val disposable = apiService.searchImages(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                nasaResponse.postValue(response)
            }, { error ->
                Log.e("MainViewModel", "Error fetching image", error)
            })
        compositeDisposable.add(disposable)
    }
}