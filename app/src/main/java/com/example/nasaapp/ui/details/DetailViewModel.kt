package com.example.nasaapp.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.domain.model.DetailedItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val nasaRepository: NasaRepository,
    private val detailedDtoToDomainMapper: DetailedDtoToDomainMapper
) : ViewModel() {
    val uiState = MutableLiveData<DetailedUiState>()
    val fileInfo = MutableLiveData<DetailedItem>()
    val videoLink = MutableLiveData<String>()

    //инкапсуляция как на SearchFragment для LiveData, она будет одна с DetailedUiState - новый класс будет iserror is loading video link и т.д
    //после assisted inject, метод вызывать в инит блоке(в самом конце)

    private val nasaIdSubject = PublishSubject.create<String>()
    private val compositeDisposable = CompositeDisposable()

    init {
        initSubscriptions()
    }

    fun setNasaId(nasaId: String){
        nasaIdSubject.onNext(nasaId)
    }

    fun initSubscriptions() {
        val detailInfoObservable = nasaIdSubject.flatMapSingle {
            nasaRepository.getResourceInfo(it)
        }

        val detailedVideoLink = nasaIdSubject.flatMapSingle {
            nasaRepository.getVideoLink(it)
        }

        val disposable = detailInfoObservable.zipWith(detailedVideoLink){
                resourceInfo, videoUrl ->
            resourceInfo to videoUrl
        }.subscribeOn(Schedulers.io())
            .subscribe({result ->
                val (resourceInfo, videoUrl) = result
                fileInfo.value = detailedDtoToDomainMapper.map(resourceInfo, videoUrl)


            }, { error ->
                fileInfo.value = DetailedItem(fileSize = "0 KB", fileFormat = "Unknown")

            })

        compositeDisposable.add(disposable)



//        val videoLinkSingle = nasaRepository.getVideoLink(nasaId)
//        val metadataUrlSingle = nasaRepository.getMetadataUrl(nasaId)
//            .map { it.location }
//        val disposable = metadataUrlSingle.flatMap { metadataUrl ->
//            nasaRepository.getResourceInfo(metadataUrl)
//        }
//            .zipWith(videoLinkSingle) { resourceInfo, videoUrl ->//Объединяем два Single resourceInfo из metadata и videoUrlиз videoLinkSingle
//                detailedDtoToDomainMapper.map(resourceInfo, videoUrl)
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ result ->
//                Log.d("DetailViewModel", "Response received: $result")
//                fileInfo.value = result
//            }, { error ->
//                error.printStackTrace()
//                fileInfo.value = DetailedItem(fileSize = "0 KB", fileFormat = "Unknown")
//            })
//
//        compositeDisposable.add(disposable)
    }

//    fun fetchVideoLink(nasaId: String) { //val video link observable и потом zip
//        val disposable = nasaRepository.getVideoLink(nasaId)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ link ->
//                videoLink.value = link
//            }, { error ->
//                Log.e("DetailViewModel", "Error fetching video link", error)
//            })
//
//        compositeDisposable.add(disposable)
//    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

data class DetailedUiState(
    val isError: Boolean = false,
    val isLoading: Boolean = true,
    val videoLink: String? = null,
    val detailedItem: DetailedItem? = null,
)