package com.example.nasaapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.ui.search.details.DetailFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val searchView: androidx.appcompat.widget.SearchView = view.findViewById(R.id.search_view)
        progressBar = view.findViewById(R.id.progress_bar)

        searchViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            progressBar.visibility = View.GONE
            if (results.isNotEmpty()) {
                recyclerView.adapter = SearchAdapter(results) {searchItem->
                    onItemClick(searchItem)
                }
            } else {
                Toast.makeText(context, "Нет данных по запросу", Toast.LENGTH_SHORT).show()
            }
        }

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    progressBar.visibility = View.VISIBLE
                    lifecycleScope.launch {
                        searchViewModel.fetchImageDetails(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn).visibility =
                    if (newText.isNullOrEmpty()) View.GONE else View.VISIBLE
                return true
            }
        })

        val closeButton = searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn)
        closeButton.setOnClickListener {
            searchView.setQuery("", false)
        }

        return view

    }
    private fun onItemClick(searchItem: SearchItem) {
        val bundle = Bundle().apply {
            putString("imageUrl", searchItem.imageUrl)
            putString("nasaId", searchItem.nasaId)
            putString("description", searchItem.description)
            putString("date", searchItem.date)
        }
        val detailFragment = DetailFragment().apply {
            arguments = bundle
        }

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, detailFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
