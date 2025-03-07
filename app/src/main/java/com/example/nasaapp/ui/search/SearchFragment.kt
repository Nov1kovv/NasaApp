package com.example.nasaapp.ui.search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.databinding.FragmentSearchBinding
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.ui.search.details.DetailFragment
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    companion object {
        const val KEY_IMAGE_URL = "imageUrl"
        const val KEY_NASA_ID = "nasaId"
        const val KEY_DESCRIPTION = "description"
        const val KEY_DATE = "date"
    }

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val toolbar: MaterialToolbar = binding.toolbar
        val recyclerView: RecyclerView = binding.recyclerView
        progressBar = binding.progressBar

        requireActivity().window.statusBarColor = Color.BLUE

        setupToolbar(toolbar)

        setupRecyclerView(recyclerView)

        observeSearchResults(recyclerView)

        observeLoadingState()

        return binding.root
    }

    private fun setupToolbar(toolbar: MaterialToolbar) {
        toolbar.setOnMenuItemClickListener { item ->
            onMenuItemClick(item)
        }
        toolbar.inflateMenu(R.menu.menu_search)
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        setupSearchView(searchView)
    }

    private fun setupSearchView(searchView: androidx.appcompat.widget.SearchView) {
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.visibility = View.GONE

        val searchTextView =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchTextView.setTextColor(Color.WHITE)
        searchTextView.setHintTextColor(Color.GRAY)

        val filterItem = binding.toolbar.menu.findItem(R.id.action_filter)
        filterItem.icon?.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN)

        searchView.queryHint = "Введите запрос..."

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onSearchQuerySubmit(query)
                return true
            }

            //переделать на UDF MVVM
            override fun onQueryTextChange(newText: String?): Boolean {
                onSearchQueryChange(newText)
                return true
            }
        })
    }

    private fun onSearchQuerySubmit(query: String?) {
        query?.let {
            progressBar.visibility = View.VISIBLE
            searchViewModel.fetchImageDetails(it)
        }
    }

    private fun onSearchQueryChange(newText: String?) {
        val filterItem = binding.toolbar.menu.findItem(R.id.action_filter)
        filterItem.isVisible = newText?.isNotEmpty() == true

        val searchItem = binding.toolbar.menu.findItem(R.id.action_search)
        searchItem.isVisible = false
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                showFilterDialog()
                true
            }

            else -> false
        }
    }

    private fun showFilterDialog() {
        val filterDialog = FilterDialogFragment()
        filterDialog.setOnFilterSelectedListener { selectedType ->
            Toast.makeText(context, "Выбран тип: $selectedType", Toast.LENGTH_SHORT)
                .show()
        }
        filterDialog.show(childFragmentManager, "filter_dialog")
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeSearchResults(recyclerView: RecyclerView) {
        searchViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            progressBar.visibility = View.GONE
            if (results.isNotEmpty()) {
                recyclerView.adapter = SearchAdapter(results) { searchItem ->
                    onItemClick(searchItem)
                }
            } else {
                Toast.makeText(context, "Нет данных по запросу", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeLoadingState() {
        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }


    private fun onItemClick(searchItem: SearchItem) {
        val bundle = Bundle().apply {
            putString(KEY_IMAGE_URL, searchItem.imageUrl)
            putString(KEY_NASA_ID, searchItem.nasaId)
            putString(KEY_DESCRIPTION, searchItem.description)
            putString(KEY_DATE, searchItem.date)
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
