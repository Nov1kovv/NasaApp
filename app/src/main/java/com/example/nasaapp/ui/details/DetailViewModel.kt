package com.example.nasaapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.domain.model.DetailedItem
import com.example.nasaapp.domain.repository.NasaRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class DetailViewModel (
    private val nasaId : String,
    private val nasaRepository: NasaRepository,
    private val detailedDtoToDomainMapper: DetailedDtoToDomainMapper
    //Создаю класс UI Factory и сюда делаю inject
) : ViewModel(), ViewModelProvider.Factory {
    private val _uiState = MutableLiveData<DetailedUiState>()
    val uiState: LiveData<DetailedUiState> get() = _uiState

    //инкапсуляция как на SearchFragment для LiveData, она будет одна с DetailedUiState - новый класс будет iserror is loading video link и т.д

    private val nasaIdSubject = PublishSubject.create<String>()
    private val compositeDisposable = CompositeDisposable()

    init {
        initSubscriptions()
    }

    fun initSubscriptions() { //observable который при получении nasa запрашивает данные
        val detailInfoObservable = nasaIdSubject
            .startWith(nasaId)
            .flatMapSingle { nasaId ->
            nasaRepository.getMetadataUrl(nasaId)//получаею url с метаданными по nasaid
                .subscribeOn(Schedulers.io())
                .map { it.location }//извлекаею location url из результата
                .flatMap { metadataUrl ->
                    nasaRepository.getResourceInfo(metadataUrl)//запрашиваем подробную информацию о ресурсе по url метаданных
                        .subscribeOn(Schedulers.io())//запрос в фоне
                }
        }

        val detailedVideoLink = nasaIdSubject
            .startWith(nasaId)
            .flatMapSingle { nasaId ->//observable который при получении nasa id запрашивает ссылку на видео
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
                val detailedItem = detailedDtoToDomainMapper.map(resourceInfo, videoUrl)
                _uiState.value = DetailedUiState(
                    isLoading = false,
                    isError = false,
                    videoLink = videoUrl,
                    detailedItem = detailedItem
                )

            }, { error ->
                Log.i("DetailViewModel333", "initSubscriptions", error)
                _uiState.value = DetailedUiState(
                    isLoading = false,
                    isError = true,
                    detailedItem = DetailedItem("","","")
                )

            })

        compositeDisposable.add(disposable)

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class Factory @AssistedInject constructor(
        @Assisted("nasaId") private val nasaId: String, private val nasaRepository: NasaRepository,
        private val detailedDtoToDomainMapper: DetailedDtoToDomainMapper
    ) : ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == DetailViewModel::class.java)
            return DetailViewModel(nasaId, nasaRepository, detailedDtoToDomainMapper) as T
        }
        @AssistedFactory
        interface DetailFactory{
            fun create (@Assisted("nasaId") nasaId: String):DetailViewModel.Factory
        }
    }
}

data class DetailedUiState(
    val isError: Boolean = false,
    val isLoading: Boolean = true,
    val videoLink: String = "",
    val detailedItem: DetailedItem ,
)