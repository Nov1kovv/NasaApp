package com.example.nasaapp.ui.single_article_details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import com.example.nasaapp.data.api.TheArticleDBClient
import com.example.nasaapp.data.value_object.NasaResponse
import com.example.nasaapp.databinding.ActivitySingleArticleBinding
import io.reactivex.disposables.CompositeDisposable

class SingleArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleArticleBinding
    private lateinit var viewModel: SingleArticleViewModel
    private lateinit var nasaResponse: NasaResponse
    //private val compositeDisposable = CompositeDisposable()
    private val apiService = TheArticleDBClient.getClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_article)
        binding = ActivitySingleArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nasaId: String = intent.getStringExtra("nasa_id").toString()
        viewModel = getViewModel(nasaId)


        viewModel.nasaResponse.observe(this) {
            bindUI(it)
        }
    }

    private fun bindUI(response: NasaResponse) {
        val imageUrl = response.collection.items.firstOrNull()?.links?.firstOrNull{ it.rel == "canonical" }?.href
        Log.d("ImageURL", "URL: $imageUrl")
        imageUrl?.let {
            Glide.with(this).load(it).into(binding.ivArticlePoster)
//                .load(""https://www.example.com/sample-image.jpg"")
//                .into(binding.ivArticlePoster)


        }
    }
    private val compositeDisposable = CompositeDisposable()

    private fun getViewModel(nasaId: String): SingleArticleViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SingleArticleViewModel(apiService, compositeDisposable, nasaId) as T
            }
        })[SingleArticleViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}