package com.example.nasaapp.ui.search.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.usecase.GetDetailedInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val getDetailedInfoUseCase: GetDetailedInfoUseCase) : ViewModel() {

    val fileInfo = MutableLiveData<DetailedFileInfo>()

    fun fetchDetailedInfo(nasaId: String) {
        viewModelScope.launch {
            fileInfo.value = getDetailedInfoUseCase.execute(nasaId)
        }
    }
}