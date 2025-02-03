package com.example.nasaapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.squareup.picasso.Picasso

class SearchAdapter(private val searchItems: List<SearchItem>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = searchItems[position]
        holder.textView.text = item.name
        holder.dateTextView.text = item.date

        Picasso.get().load(item.imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = searchItems.size
}
