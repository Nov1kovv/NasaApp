package com.example.nasaapp.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class DownloadButtonDelegate(
    private val onDownloadClick: (String) -> Unit
) : AdapterDelegate<List<DetailItem>>() {

    override fun isForViewType(items: List<DetailItem>, position: Int): Boolean =
        items[position] is DetailItem.DownloadButtonItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_download_button, parent, false)
        return DownloadButtonViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<DetailItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as DetailItem.DownloadButtonItem
        (holder as DownloadButtonViewHolder).bind(item)
    }

    inner class DownloadButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val downloadButton: Button = view.findViewById(R.id.downloadButton)

        fun bind(item: DetailItem.DownloadButtonItem) {
            downloadButton.setOnClickListener { onDownloadClick(item.url) }
        }
    }
}