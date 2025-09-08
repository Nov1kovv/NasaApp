package com.example.nasaapp.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class PhotoDelegate : AdapterDelegate<List<DetailItem>>() {

    override fun isForViewType(items: List<DetailItem>, position: Int): Boolean =
        items[position] is DetailItem.Photo

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
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
        private val image = view.findViewById<ImageView>(R.id.photoView)

        fun bind(item: DetailItem.Photo) {
            Glide.with(itemView.context).load(item.imageUrl).into(image)
        }
    }
}