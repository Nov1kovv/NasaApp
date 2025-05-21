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
        nasaIdSubject.onNext(nasaId)// отправляю новое значение nasaid в Subject
    }

    fun initSubscriptions() { //observable который при получении nasa запрашивает данные
        val detailInfoObservable = nasaIdSubject.flatMapSingle { nasaId ->
            nasaRepository.getMetadataUrl(nasaId)//получаею url с метаданными по nasaid
                .subscribeOn(Schedulers.io())
                .map { it.location }//извлекаею location url из результата
                .flatMap { metadataUrl ->
                    nasaRepository.getResourceInfo(metadataUrl)//запрашиваем подробную информацию о ресурсе по url метаданных
                        .subscribeOn(Schedulers.io())//запрос в фоне
                }
        }

        val detailedVideoLink = nasaIdSubject.flatMapSingle { nasaId ->//observable который при получении nasa id запрашивает ссылку на видео
            Log.d("DetailViewModelVideooo", "Link $nasaId")
            nasaRepository.getVideoLink(nasaId)
                .subscribeOn(Schedulers.io())
        }

        val disposable = detailInfoObservable
            .observeOn(Schedulers.io())
            .zipWith(detailedVideoLink){//объединяет два Observable информацию о ресурсе и ссылку на видео
                resourceInfo, videoUrl ->
            resourceInfo to videoUrl//Возвращает пару информация о ресурсе и ссылка на видео
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                val (resourceInfo, videoUrl) = result
                Log.i("DetailViewModel111", "Video URL $videoUrl")
                Log.i("DetailViewModel222", "Resource info $resourceInfo")
                fileInfo.value = detailedDtoToDomainMapper.map(resourceInfo, videoUrl)
                videoLink.value = videoUrl

            }, { error ->
                Log.i("DetailViewModel333", "initSubscriptions", error)
                fileInfo.value = DetailedItem(fileSize = "0 KB", fileFormat = "Unknown")

            })

        compositeDisposable.add(disposable)

    }


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