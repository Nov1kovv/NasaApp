package com.example.nasaapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor (private val repository: NasaRepository) : ViewModel() {
    private val _searchResult = MutableLiveData<UiState>()//здесь хранится state
    val searchResult: LiveData<UiState> = _searchResult

    private val _errorToast = MutableLiveData<Unit>()
    val errorToast: LiveData<Unit> = _errorToast

    private val compositeDisposable = CompositeDisposable()

    private val querySubject = PublishSubject.create<Pair<String, String>>()

    init {
        observeQuerySubject() //подписываюсь
    }

    fun emitSearchQuery(query: String, mediaType: String) { //метод который эммитит eventы в Subject
        querySubject.onNext(query to mediaType)
    }

    private fun observeQuerySubject() {
        val disposable = querySubject.startWith(Pair("nasa", "image"))
            .debounce(300, TimeUnit.MILLISECONDS) //отсрочить выполнение действия на 200 милисекунд
            .filter { it.first.isNotBlank() } //если строчка пустая, то пропускаю, ищу только заполненую строчку
            .distinctUntilChanged() //игнорирую запросы которые одинаковые с прошлым
            .switchMapSingle { (query, mediaType) ->
                repository.searchImages(query, mediaType)
            }
            .subscribeOn(Schedulers.io())
            .subscribe({ items ->
                _searchResult.postValue(UiState(items = items, isLoading = false))
            }, { error ->
                Log.e("SearchViewModel", "Error search", error)  // Печатаем подробный лог ошибки
                _searchResult.postValue(UiState(error = true, isLoading = false))
                _errorToast.postValue(Unit)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

class UiState(
    val error: Boolean = false,
    val isLoading: Boolean = true,
    val items: List<SearchItem> = emptyList()
)