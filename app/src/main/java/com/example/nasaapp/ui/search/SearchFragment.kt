package com.example.nasaapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchItems = List(100) { SearchItem("Строка${it + 1}",
            "https://avatars.mds.yandex.net/i?id=c73ba79f654009e2cac2a6e885c19368_l-5334002-images-thumbs&n=13",
            "03.02.2025")}
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = SearchAdapter(searchItems)

        return view
    }
}