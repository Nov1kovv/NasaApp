package com.example.nasaapp.ui.test

import javax.inject.Inject


class ContractImpl @Inject constructor() : Contract {
    override fun log() {
        println("Hello world!")
    }
}