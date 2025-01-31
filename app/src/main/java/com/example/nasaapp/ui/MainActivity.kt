package com.example.nasaapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import com.example.nasaapp.data.api.TheArticleDBClient
import com.example.nasaapp.ui.single_article_details.DetailsViewModel
import com.example.nasaapp.ui.single_article_details.DetailsViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(TheArticleDBClient.getClient())
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Посмотреть утечку в LeakCanary
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btn)
        imageView = findViewById(R.id.iv_article_poster)

        button.setOnClickListener {
            Toast.makeText(this, "Fetching Image...", Toast.LENGTH_SHORT).show()
            viewModel.fetchImageDetails("moon")
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.nasaResponse.observe(this) { response ->
            response?.let {
                val imageUrl = it.collection.items.firstOrNull()?.links?.firstOrNull()?.href
                Log.d("MainActivity", "Image URL: $imageUrl")

                if (!imageUrl.isNullOrEmpty()) {
                    Glide.with(this).load(imageUrl).into(imageView)
                } else {
                    Toast.makeText(this, "Image not found!", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "Failed to load data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
