package com.example.nasaapp.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.domain.model.DetailedItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val nasaRepository: NasaRepository, private val detailedDtoToDomainMapper: DetailedDtoToDomainMapper) : ViewModel() {

    val fileInfo = MutableLiveData<DetailedItem>()
    val videoLink = MutableLiveData<String>()

    private val compositeDisposable = CompositeDisposable()

    fun fetchDetailedInfo(nasaId: String) {
        val videoLinkSingle = nasaRepository.getVideoLink(nasaId)
        val metadataUrlSingle = nasaRepository.getMetadataUrl(nasaId)
            .map { it.location }
        val disposable = metadataUrlSingle.flatMap { metadataUrl ->
            nasaRepository.getResourceInfo(metadataUrl)
        }
            .zipWith(videoLinkSingle) { resourceInfo, videoUrl ->//Объединяем два Single resourceInfo из metadata и videoUrlиз videoLinkSingle
                detailedDtoToDomainMapper.map(resourceInfo, videoUrl)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("DetailViewModel", "Response received: $result")
                fileInfo.value = result
            }, { error ->
                error.printStackTrace()
                fileInfo.value = DetailedItem(fileSize = "0 KB", fileFormat = "Unknown")
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