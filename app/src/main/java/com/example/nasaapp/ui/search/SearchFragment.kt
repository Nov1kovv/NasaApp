package com.example.nasaapp.ui.search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.ui.search.details.DetailFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var progressBar: ProgressBar
    private var selectedMediaType: String = "image"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val toolbar: MaterialToolbar = view.findViewById(R.id.toolbar)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView.layoutManager = LinearLayoutManager(context)
        requireActivity().window.statusBarColor = Color.BLUE


    toolbar.inflateMenu(R.menu.menu_search)
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.visibility = View.GONE

        val searchTextView =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchTextView.setTextColor(Color.WHITE)
        searchTextView.setHintTextColor(Color.GRAY)

        val filterItem = toolbar.menu.findItem(R.id.action_filter)
        filterItem.icon?.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN)

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
        performSearch("nasa")

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

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    progressBar.visibility = View.VISIBLE
                    lifecycleScope.launch {
                        performSearch(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filterItem = toolbar.menu.findItem(R.id.action_filter)
                filterItem.isVisible = newText?.isNotEmpty() == true

                val searchItem = toolbar.menu.findItem(R.id.action_search)
                searchItem.isVisible = false
                return true
            }
        })
        return view
    }

    private fun performSearch(query: String) {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            searchViewModel.fetchImageDetails(query, selectedMediaType)
        }
    }

    private fun onItemClick(searchItem: SearchItem) {
        val bundle = Bundle().apply {
            putString("imageUrl", searchItem.imageUrl)
            putString("nasaId", searchItem.nasaId)
            putString("description", searchItem.description)
            putString("date", searchItem.date)
            putBoolean("isVideo", searchItem.isVideo)
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
