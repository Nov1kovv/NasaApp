package com.example.nasaapp.ui.details.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class DetailAdapter(
    onDownloadClick: (String) -> Unit,
    onShareClick: (String) -> Unit
) : ListDelegationAdapter<List<DetailItem>>() {

    init {
        delegatesManager
            .addDelegate(PhotoDelegate())
            .addDelegate(VideoDelegate(onDownloadClick, onShareClick))
            .addDelegate(TextDelegate())
    }
}