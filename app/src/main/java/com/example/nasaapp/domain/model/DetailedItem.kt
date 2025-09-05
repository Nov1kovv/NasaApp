package com.example.nasaapp.domain.model

data class DetailedItem(
    val fileSize: String,
    val fileFormat: String,
    val videoUrl: String? = null,
    val detailedItem: DetailedItem? = null
)

/**
 * делаем делатлку на адаптер делегате, а поиск не трогаем //
 * В макете xml будет только recyclerview внутри liner layout //
 * Каждая view это теперь отдельный viewholder который будет рисовать recyclerview //
 * Для recyclrer view нужно будет отдать state list<ItemModel> //
 * для начала нужно убрать верстку xml старую и сделать recycler view, наполнить экран списком из 10
 * заголовков хард кодом
 *
 *
 * */