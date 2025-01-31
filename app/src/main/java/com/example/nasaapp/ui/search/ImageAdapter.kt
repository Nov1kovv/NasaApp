package com.example.nasaapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapp.data.value_object.Item
import com.example.nasaapp.databinding.ItemImageBinding

// Адаптер для RecyclerView, который отображает список изображений
class ImageAdapter : ListAdapter<Item, ImageAdapter.ImageViewHolder>(ItemDiffCallback()) {

    // Создание нового ViewHolder (карточки элемента списка)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    // Привязка данных к конкретному ViewHolder
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    // ViewHolder для одного изображения
    class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            val imageUrl = item.links.firstOrNull()?.href
            Glide.with(binding.root.context).load(imageUrl).into(binding.imageView)
        }
    }
    // Позволяет RecyclerView определять разницу между старыми и новыми элементами списка
    class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.href == newItem.href
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem // Полное сравнение объектов
        }
    }
}