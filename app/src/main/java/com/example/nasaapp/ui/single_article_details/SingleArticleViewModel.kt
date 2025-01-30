package com.example.nasaapp.ui.single_article_details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.value_object.NasaResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SingleArticleViewModel(
    private val apiService: TheArticleDBInterface,
    private val compositeDisposable: CompositeDisposable,
    nasaId: String
) : ViewModel() {
    val nasaResponse = androidx.lifecycle.MutableLiveData<NasaResponse>()

    init {
        fetchImageDetails(nasaId)
    }

    private fun fetchImageDetails(query: String) {
        val disposable = apiService.searchImages(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                nasaResponse.postValue(response)
            }, { error ->
                Log.e("SingleArticleViewModel", "Error fetching image details", error)
            })

        compositeDisposable.add(disposable) // теперь .add не будет гореть красным
    }
}