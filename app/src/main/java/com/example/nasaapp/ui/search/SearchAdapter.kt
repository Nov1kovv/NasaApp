package com.example.nasaapp.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.domain.model.SearchItem

class SearchAdapter(private var items: List<SearchItem>, private val action: (SearchItem) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {


        class SearchViewHolder(val customView: CustomSearchItemView) :
        RecyclerView.ViewHolder(customView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder { //создание ViewHolder
        val view = CustomSearchItemView(parent.context)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) { //происходит отрисовка всех элементов
        val item = items[position]
        holder.customView.bind(item.description, item.date, item.imageUrl, item.isVideo)

        holder.customView.imageView.setOnClickListener {
            action.invoke(item)
        }
    }
    override fun getItemCount(): Int = items.size //возвращает количество элементов списка с данными

    fun updateItems(newItems: List<SearchItem>) {
        this.items = newItems
        notifyDataSetChanged() //обновляет все элементы
    }
}