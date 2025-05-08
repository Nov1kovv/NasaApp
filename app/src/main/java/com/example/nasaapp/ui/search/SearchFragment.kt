package com.example.nasaapp.ui.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.widget.SearchView
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.domain.model.SearchItem
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var progressBar: ProgressBar
    private var selectedMediaType: String = "image"
    private val disposables = CompositeDisposable()
    private lateinit var adapter: SearchAdapter

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

        return view
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //если искал до этого что-то, то показываю этот запрос, если значение пустое то показываю mars
        val lastQuery = searchViewModel.getLastQuery()
        val queryToSearch = if (lastQuery.isNotEmpty()) lastQuery else "mars"
            performSearch(queryToSearch)

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
        adapter = SearchAdapter (emptyList()){ searchItem -> onItemClick(searchItem) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        }



    private fun performSearch(query: String) {
        progressBar.visibility = View.VISIBLE
            //disposables используется для управления подписками
        disposables.clear()//очищаю старые подписки
        val disposable = searchViewModel.searchImages(query, selectedMediaType)//запрос к API через viewmodel
            .subscribeOn(Schedulers.io()) // выполняем в фоновом потоке, чтобы не заблокировать главный поток
            .observeOn(AndroidSchedulers.mainThread()) // результат обрабатываю на главном
            .subscribe({ items -> //подписка не результат
                progressBar.visibility = View.GONE
                adapter.updateItems(items)//обновляю адаптер новыми элементами которые пришли с сервера
            }, { error ->
                progressBar.visibility = View.GONE
                Log.e("SearchFragment", "Error ${error.message}")
            })

        disposables.add(disposable)//добавляю disposable в контейнер CompositeDisposable, чтобы потом управлять подписками и очистить их все сразу, если нужно будет
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
    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}




