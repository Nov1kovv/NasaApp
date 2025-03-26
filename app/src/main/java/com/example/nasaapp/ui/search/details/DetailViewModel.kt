package com.example.nasaapp.ui.search.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapp.domain.model.DetailedFileInfo
import com.example.nasaapp.domain.usecase.GetDetailedInfoUseCase
import com.example.nasaapp.domain.usecase.GetVideoLinkUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getDetailedInfoUseCase: GetDetailedInfoUseCase,
    private val getVideoLinkUseCase: GetVideoLinkUseCase
) : ViewModel() {

    val fileInfo = MutableLiveData<DetailedFileInfo>()
    val videoLink = MutableLiveData<String>()

    fun fetchDetailedInfo(nasaId: String) {
        viewModelScope.launch {
            try {
                Log.d("DetailViewModel", "Fetching details for NASA ID: $nasaId")

                val result = getDetailedInfoUseCase.execute(nasaId)

                Log.d("DetailViewModel", "Response received: $result")

                fileInfo.value = result

            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error fetching details", e)

                fileInfo.value = DetailedFileInfo(fileSize = "0 KB", fileFormat = "Unknown")
            }
        }
    }

    fun fetchVideoLink(nasaId: String) {
        viewModelScope.launch {
            try {
                val link = getVideoLinkUseCase.execute(nasaId)
                videoLink.value = link
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error fetching video link", e)
            }
        }
    }
}