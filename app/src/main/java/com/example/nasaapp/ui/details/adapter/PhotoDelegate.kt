package com.example.nasaapp.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class PhotoDelegate(
    private val onDownloadClick: (String) -> Unit,
    private val onShareClick: (String) -> Unit
) : AdapterDelegate<List<DetailItem>>() {

    override fun isForViewType(items: List<DetailItem>, position: Int): Boolean =
        items[position] is DetailItem.Photo

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_search, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<DetailItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as DetailItem.Photo
        (holder as PhotoViewHolder).bind(item)
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.imageView)
        private val description = view.findViewById<TextView>(R.id.deschription)
        private val download = view.findViewById<ImageButton>(R.id.download_button)
        private val share = view.findViewById<ImageButton>(R.id.share_button)

        fun bind(item: DetailItem.Photo) {
            Glide.with(itemView.context).load(item.imageUrl).into(image)
            description.text = item.description

            download.setOnClickListener { onDownloadClick(item.imageUrl) }
            share.setOnClickListener { onShareClick(item.imageUrl) }
        }
    }
}