package com.example.nasaapp.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class ShareButtonDelegate(
    private val onShareClick: (String) -> Unit
) : AdapterDelegate<List<DetailItem>>() {

    override fun isForViewType(items: List<DetailItem>, position: Int): Boolean =
        items[position] is DetailItem.ShareButtonItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_share_button, parent, false)
        return ShareButtonViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<DetailItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as DetailItem.ShareButtonItem
        (holder as ShareButtonViewHolder).bind(item)
    }

    inner class ShareButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val shareButton: Button = view.findViewById(R.id.shareButton)

        fun bind(item: DetailItem.ShareButtonItem) {
            shareButton.setOnClickListener { onShareClick(item.url) }
        }
    }
}