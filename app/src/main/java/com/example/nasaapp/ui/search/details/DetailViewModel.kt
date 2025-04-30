package com.example.nasaapp.ui.search.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val nasaRepository: NasaRepository) : ViewModel() {

    val fileInfo = MutableLiveData<DetailedFileInfo>()
    val videoLink = MutableLiveData<String>()

    private val compositeDisposable = CompositeDisposable()

    fun fetchDetailedInfo(nasaId: String) {
        val disposable = nasaRepository.getDetailedInfo(nasaId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("DetailViewModel", "Response received: $result")
                fileInfo.value = result
            }, { error ->
                error.printStackTrace()
                fileInfo.value = DetailedFileInfo(fileSize = "0 KB", fileFormat = "Unknown")
            })

        compositeDisposable.add(disposable)
    }

    fun fetchVideoLink(nasaId: String) {
        val disposable = nasaRepository.getVideoLink(nasaId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ link ->
                videoLink.value = link
            }, { error ->
                Log.e("DetailViewModel", "Error fetching video link", error)
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}