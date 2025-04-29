package com.example.nasaapp.ui.search

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.widget.SearchView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.data.paging.SearchPagingAdapter
import com.example.nasaapp.domain.model.SearchItem
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var adapter: SearchPagingAdapter
    private lateinit var progressBar: ProgressBar
    private var selectedMediaType: String = "image"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val toolbar: MaterialToolbar = view.findViewById(R.id.toolbar)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        requireActivity().window.statusBarColor = Color.BLUE

        setupToolbar(toolbar)
        setupRecyclerView(recyclerView)
        observeSearchResults("")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lastQuery = searchViewModel.getLastQuery()
        if (lastQuery.isNotEmpty()) {
            performSearch(lastQuery)
        }
    }

    private fun setupToolbar(toolbar: MaterialToolbar) {
        toolbar.inflateMenu(R.menu.menu_search)

        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.visibility = View.GONE

        val searchTextView = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchTextView.setTextColor(Color.WHITE)
        searchTextView.setHintTextColor(Color.GRAY)

        val filterItem = toolbar.menu.findItem(R.id.action_filter)
        filterItem.icon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)

        searchView.queryHint = "Введите запрос..."

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_filter -> {
                    val filterDialog = FilterDialogFragment()
                    filterDialog.setOnFilterSelectedListener { selectedType ->
                        selectedMediaType = selectedType
                        performSearch(searchView.query.toString())
                    }
                    filterDialog.show(childFragmentManager, "filter_dialog")
                    true
                }
                else -> false
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { performSearch(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItem.isVisible = newText?.isNotEmpty() == true
                searchItem.isVisible = false
                return true
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = SearchPagingAdapter { searchItem -> onItemClick(searchItem) }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.addLoadStateListener { loadStates ->
            progressBar.visibility = if (loadStates.refresh is LoadState.Loading) View.VISIBLE else View.GONE
        }
    }

    private fun observeSearchResults(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                searchViewModel.searchImages(query, selectedMediaType).collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun performSearch(query: String) {
        progressBar.visibility = View.VISIBLE
        adapter.refresh()
        observeSearchResults(query)
    }

    private fun onItemClick(searchItem: SearchItem) {
        val bundle = Bundle().apply {
            putString("imageUrl", searchItem.imageUrl)
            putString("nasaId", searchItem.nasaId)
            putString("description", searchItem.description)
            putString("date", searchItem.date)
            putBoolean("isVideo", searchItem.isVideo)
        }
        findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
    }
}
