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
import com.example.nasaapp.databinding.ViewSearchItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CustomSearchItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewSearchItemBinding =
        ViewSearchItemBinding.inflate(LayoutInflater.from(context), this, true)

    fun bind(description: String, date: String, url: String) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val parsedDate = inputFormat.parse(date)
            val formattedDate = parsedDate?.let { dateFormat.format(it) } ?: date
            binding.date.text = formattedDate
        } catch (e: Exception) {
            Log.e("ERROR", "Something went wrong", e);
        }

        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(binding.customImageView)

        binding.deschription.text = description
    }
}
