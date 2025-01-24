package com.example.nasaapp.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}
class NetworkState(val status: Status, val msg: String) {
    val LOADED: NetworkState
    val LOADING: NetworkState
    val ERROR: NetworkState

    init {
        LOADED = NetworkState(Status.SUCCESS, "Success")
        LOADING = NetworkState(Status.SUCCESS, "Running")
        ERROR = NetworkState(Status.SUCCESS, "Something went wrong")
    }
}