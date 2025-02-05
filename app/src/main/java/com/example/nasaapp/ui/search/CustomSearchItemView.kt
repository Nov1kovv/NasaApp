package com.example.nasaapp.ui.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.nasaapp.R


class CustomSearchItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val imageView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search_item, this, true)
        imageView = findViewById(R.id.customImageView)
    }
}
