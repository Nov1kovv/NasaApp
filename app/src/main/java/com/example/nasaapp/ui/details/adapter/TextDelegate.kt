package com.example.nasaapp.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class TextDelegate : AdapterDelegate<List<DetailItem>>() {

    override fun isForViewType(items: List<DetailItem>, position: Int): Boolean =
        items[position] is DetailItem.TextItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<DetailItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as DetailItem.TextItem
        (holder as TextViewHolder).bind(item)
    }

    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.textView)

        fun bind(item: DetailItem.TextItem) {
            textView.text = item.text
        }
    }
}