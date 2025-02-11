package com.example.nasaapp.ui.search

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import java.text.SimpleDateFormat
import java.util.Locale


class CustomSearchItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val imageView: ImageView
    private val descriptionView: TextView
    private val dateView: TextView
    init {
        LayoutInflater.from(context).inflate(R.layout.view_search_item, this, true)
        imageView = findViewById(R.id.customImageView)
        descriptionView = findViewById(R.id.deschription)
        dateView = findViewById(R.id.date)
    }
    fun bind(description: String, date: String, url: String) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        try {
            val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(date)
            val formattedDate = parsedDate?.let { dateFormat.format(it) } ?: date
            dateView.text = formattedDate
        } catch (e: Exception) {
            Log.e("ERROR", "Something went wrong", e);
        }

        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(imageView)

        descriptionView.text = description
    }
}
