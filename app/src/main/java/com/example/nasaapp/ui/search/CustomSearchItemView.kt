package com.example.nasaapp.ui.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.nasaapp.R
import com.squareup.picasso.Picasso

class CustomSearchItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val textView: TextView
    private val dateTextView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search_item, this, true)
        imageView = findViewById(R.id.customImageView)
        textView = findViewById(R.id.customTextView)
        dateTextView = findViewById(R.id.customDateTextView)
    }

    fun bind(name: String, imageUrl: String, date: String) {
        textView.text = name
        dateTextView.text = date
        Picasso.get().load(imageUrl).into(imageView)
    }
}