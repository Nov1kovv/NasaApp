package com.example.nasaapp.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}
class NetworkState(val status: Status, val msg: String) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS, "Success")
        val LOADING = NetworkState(Status.RUNNING, "Loading")
        val ERROR = NetworkState(Status.FAILED, "Something went wrong")
    }
}