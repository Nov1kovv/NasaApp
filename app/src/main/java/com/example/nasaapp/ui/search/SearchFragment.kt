package com.example.nasaapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.data.api.TheArticleDBClient

// Фрагмент для поиска изображений
class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel // ViewModel для управления данными
    private lateinit var searchView: SearchView // Поле поиска
    private lateinit var recyclerView: RecyclerView // Список найденных изображений
    private val adapter = ImageAdapter() // Адаптер для RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Инициализация ViewModel с фабрикой
        viewModel = ViewModelProvider(
            this,
            SearchViewModelFactory(TheArticleDBClient.getClient())
        )[SearchViewModel::class.java]

        searchView = view.findViewById(R.id.search_view)
        recyclerView = view.findViewById(R.id.recycler_view)
        // Настройка RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        observeViewModel() // Подписываемся на изменения данных

        // Настраиваем слушатель для поля поиска
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchImages(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
    // Наблюдение за данными из ViewModel
    private fun observeViewModel() {
        val observe = viewModel.nasaResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                val items = it.collection.items
                if (items.isEmpty()) {
                    Toast.makeText(context, "No images found", Toast.LENGTH_SHORT).show()
                }
                adapter.submitList(items)
            } ?: run {
                Toast.makeText(context, "Failed to load data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}