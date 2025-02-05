package com.example.nasaapp.ui.search

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapp.data.value_object.Item

class SearchAdapter(private val items: List<Item>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    class SearchViewHolder(val customView: CustomSearchItemView) : RecyclerView.ViewHolder(customView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = CustomSearchItemView(parent.context)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = items[position]
        Log.d("ITEM", "Item: ${item.links.first().href}")
        Glide.with(holder.customView.context)
            .load(item.links.first().href)
            .into(holder.customView.imageView)

        if (item.links.isNotEmpty()) {
            Glide.with(holder.customView.context)
                .load(item.links.first().href)
                .into(holder.customView.imageView)
        }
    }


    override fun getItemCount(): Int = items.size
}
