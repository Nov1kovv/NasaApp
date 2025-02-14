package com.example.nasaapp.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

        holder.customView.bind(item.description, item.date, item.imageUrl)
    }

    override fun getItemCount(): Int = items.size
}
