package com.example.nasaapp.ui.single_article_details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import com.example.nasaapp.data.api.IMAGE_BASE_URL
import com.example.nasaapp.data.api.TheArticleDBClient
import com.example.nasaapp.data.api.TheArticleDBInterface
import com.example.nasaapp.data.repository.NetworkState
import com.example.nasaapp.data.value_object.ArticleDetails
import com.example.nasaapp.databinding.ActivitySingleArticleBinding

class SingleArticle : AppCompatActivity() {
    private lateinit var binding: ActivitySingleArticleBinding
    private lateinit var viewModel: SingleArticleViewModel
    private lateinit var articleRepository: ArticleDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_article)

        binding = ActivitySingleArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nasaId: String = intent.getStringExtra("nasa_id").toString()

        val apiService : TheArticleDBInterface = TheArticleDBClient.getClient()
        articleRepository = ArticleDetailsRepository(apiService)

        viewModel = getViewModel(nasaId)

        viewModel.articleDetails.observe(this) {
            bindUI(it)
        }
        viewModel.networkState.observe(this) {
            binding.progressBar.isVisible = it == NetworkState.LOADING
            binding.txtError.isVisible = it == NetworkState.ERROR
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun bindUI(it:ArticleDetails){

    val articlePosterURL = IMAGE_BASE_URL + it.collection.href
        Glide.with(this)
            .load(articlePosterURL)
            .into(binding.ivArticlePoster)

    }


    private fun getViewModel(nasaid:String): SingleArticleViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleArticleViewModel(articleRepository, nasaid) as T

            }
        }) [SingleArticleViewModel::class.java]
    }
    }