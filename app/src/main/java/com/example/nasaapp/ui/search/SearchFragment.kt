package com.example.nasaapp.ui.search

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.ui.details.DaggerDetailComponent
import com.example.nasaapp.ui.details.DetailViewModel
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class SearchFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private var selectedMediaType: String = "image"
    private lateinit var adapter: SearchAdapter

    @Inject
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerDetailComponent.factory().create().inject(this)
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val toolbar: MaterialToolbar = view.findViewById(R.id.toolbar)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        requireActivity().window.statusBarColor = Color.BLUE
        setupToolbar(toolbar)
        setupRecyclerView(recyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.searchResult.observe(viewLifecycleOwner) { uiState ->
            progressBar.isVisible = uiState.isLoading
            /**
             * данные не загрузились, должен быть отдельный item в recyclerView, для него нужно сделать свой viewholder, decorator для recycler
             * xml.
             * и еще снизу сделать toast как side эффект, проверьте подключение к интернету
             * side эффект отдельная livedata, другая livedata для state*/

            adapter.updateItems(uiState.items)
        }
        searchViewModel.errorToast.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                "Произошла ошибка, проверьте подключение к интернету.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupToolbar(toolbar: MaterialToolbar) {
        toolbar.inflateMenu(R.menu.menu_search)

        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.visibility = View.GONE

        val searchTextView =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
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
                        searchViewModel.emitSearchQuery(
                            searchView.query.toString(),
                            selectedMediaType
                        )
                    }
                    filterDialog.show(childFragmentManager, "filter_dialog")
                    true
                }

                else -> false
            }
        }

        /**
         *в onQueryTextChange вызываю метод ViewModel, который эммитит eventы в Subject во ViewModel
         * потом подписываюсь на этот subject в отдельном методе ViewModel, который делает switchMap и в subscribe
         * пушит данные в LiveData
         * подписку можно сделать в методе init во ViewModel
         * фрагмент только наблюдает за данными, который приходит в LiveData, пример в DetailFragment
         * */
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchViewModel.emitSearchQuery(it, selectedMediaType) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItem.isVisible = newText?.isNotEmpty() == true
                searchItem.isVisible = false
                searchViewModel.emitSearchQuery(
                    newText.orEmpty(),
                    selectedMediaType
                )//подпискана метод который эммитит eventы в Subject во ViewModel
                return true
            }
        })
        //вся бизнес логика должна быть во viewmodel. state должен лежать в livedata, фрагмент наблюдает за livedata. UDF MVVM
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = SearchAdapter(emptyList()) { searchItem -> onItemClick(searchItem) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
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




