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

class SearchViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _searchResult = MutableLiveData<List<SearchItem>>()
    val searchResult: LiveData<List<SearchItem>> = _searchResult

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
            .debounce(1, TimeUnit.MILLISECONDS) //отсрочить выполнение действия на 200 милисекунд
            .filter { it.first.isNotBlank() } //если строчка пустая, то пропускаю, ищу только заполненую строчку
            .distinctUntilChanged() //игнорирую запросы которые одинаковые с прошлым
            .switchMapSingle { (query, mediaType) ->
                repository.searchImages(query, mediaType)
            }
            .subscribeOn(Schedulers.io())
            .subscribe({ items ->
                _searchResult.postValue(items)
            }, { error ->
                Log.e("SearchFragment", "Error", error)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
