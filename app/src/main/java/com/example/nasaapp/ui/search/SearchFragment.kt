package com.example.nasaapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.data.api.TheArticleDBClient
import com.example.nasaapp.data.repository.ArticleDetailsNetworkDataSource
import com.example.nasaapp.data.repository.Status
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val searchView: androidx.appcompat.widget.SearchView = view.findViewById(R.id.search_view)

        val apiService = TheArticleDBClient.getClient()
        val networkDataSource = ArticleDetailsNetworkDataSource(apiService)

        searchViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(networkDataSource) as T
                }
            }
        ).get(SearchViewModel::class.java)

        searchViewModel.downloadedArticleResponse.observe(viewLifecycleOwner) { response ->
            if (response != null && response.collection?.items != null && response.collection.items.isNotEmpty()) {
                Log.d("API_RESPONSE", "Items: ${response.collection.items.size}")
                recyclerView.adapter = SearchAdapter(response.collection.items)
            } else {
                Toast.makeText(context, "Нет данных по запросу", Toast.LENGTH_SHORT).show()
            }
        }

        searchViewModel.networkState.observe(viewLifecycleOwner) { networkState ->
            when (networkState.status) {
                Status.RUNNING -> {
                    Toast.makeText(context, "running", Toast.LENGTH_SHORT).show()
                }

                Status.SUCCESS -> {
                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                }

                Status.FAILED -> {
                    Toast.makeText(context, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
                }
            }
        }

                searchView.setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            lifecycleScope.launch {
                                searchViewModel.fetchImageDetails(it)
                            }
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })

                    return view
            }
        }
