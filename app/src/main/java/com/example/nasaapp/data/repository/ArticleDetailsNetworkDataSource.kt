package com.example.nasaapp.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.value_object.NasaResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ArticleDetailsNetworkDataSource (private val apiService : TheArticleDBInterface, private val compositeDisposable: CompositeDisposable) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
        get() = _networkState

    private val _downloadedNasaResponseResponse = MutableLiveData<NasaResponse>()
    val downloadedArticleResponse : LiveData<NasaResponse>
        get() = _downloadedNasaResponseResponse

    fun fetchImageDetails(nasaId: String){
        _networkState.postValue(NetworkState.LOADING)

        try {
        compositeDisposable.add(
            apiService.searchImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {_downloadedNasaResponseResponse
                     _networkState.postValue(NetworkState(Status.SUCCESS, "Success $it"))
                    },
                    {
                        _networkState.postValue(NetworkState(Status.FAILED, "Error $it"))
                    }
                )
        )
    }catch (_: Exception){
    }
    }
}