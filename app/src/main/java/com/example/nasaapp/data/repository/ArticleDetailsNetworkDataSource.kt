package com.example.nasaapp.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.value_object.NasaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ArticleDetailsNetworkDataSource(
    private val apiService: TheArticleDBInterface,
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedNasaResponseResponse = MutableLiveData<NasaResponse>()
    val downloadedArticleResponse: LiveData<NasaResponse>
        get() = _downloadedNasaResponseResponse

    suspend fun fetchImageDetails(query: String) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            val response = withContext(Dispatchers.IO) {
                apiService.searchImages(query)
            }

            Log.d("API_RESPONSE", "Response: $response")
            _downloadedNasaResponseResponse.postValue(response)
            _networkState.postValue(NetworkState(Status.SUCCESS, "Success"))
        } catch (ex: Exception) {
            _networkState.postValue(NetworkState.ERROR)
            ex.printStackTrace()
        }
    }
}