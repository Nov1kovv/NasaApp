package com.example.nasaapp.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(private val searchItems: List<SearchItem>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(val customView: CustomSearchItemView) : RecyclerView.ViewHolder(customView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = CustomSearchItemView(parent.context)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = searchItems[position]
        holder.customView.bind(item.name, item.imageUrl, item.date)
    }

    override fun getItemCount(): Int = searchItems.size
}
