package com.example.nasaapp.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.value_object.ArticleDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ArticleDetailsNetworkDataSource (private val apiService : TheArticleDBInterface, private val compositeDisposable: CompositeDisposable) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
        get() = _networkState

    private val _downloadedArticleDetailsResponse = MutableLiveData<ArticleDetails>()
    val downloadedArticleResponse : LiveData<ArticleDetails>
        get() = _downloadedArticleDetailsResponse

    fun fetchImageDetails(nasaId: String){
        _networkState.postValue(NetworkState(Status.RUNNING, "Success").LOADING)

        try {
        compositeDisposable.add(
            apiService.getNasaId(nasaId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {_downloadedArticleDetailsResponse
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