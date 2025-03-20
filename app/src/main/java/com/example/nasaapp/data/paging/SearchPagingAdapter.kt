package com.example.nasaapp.data.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.ui.search.CustomSearchItemView

class SearchPagingAdapter(private val action: (SearchItem) -> Unit) :
    PagingDataAdapter<SearchItem, SearchPagingAdapter.SearchViewHolder>(DiffCallback()) {

    class SearchViewHolder(val customView: CustomSearchItemView) :
        RecyclerView.ViewHolder(customView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(CustomSearchItemView(parent.context))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.customView.bind(it.description, it.date, it.imageUrl, it.isVideo)
            holder.customView.imageView.setOnClickListener { action.invoke(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) =
            oldItem.nasaId == newItem.nasaId

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem) =
            oldItem == newItem
    }
}
