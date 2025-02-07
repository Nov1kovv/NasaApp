package com.example.nasaapp.ui.search

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapp.domain.model.SearchItem

class SearchAdapter(private val items: List<SearchItem>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    class SearchViewHolder(val customView: CustomSearchItemView) :
        RecyclerView.ViewHolder(customView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = CustomSearchItemView(parent.context)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = items[position]

        Log.d("ITEM", "Image URL: ${item.imageUrl}")
        Glide.with(holder.customView.context)
                .load(item.imageUrl)
                .into(holder.customView.imageView)
        }

    override fun getItemCount(): Int = items.size
}
