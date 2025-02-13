package com.example.nasaapp.ui.search

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.example.nasaapp.domain.model.SearchItem

class SearchAdapter(private val items: List<SearchItem>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

        class SearchViewHolder(val customView: CustomSearchItemView) :
        RecyclerView.ViewHolder(customView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = CustomSearchItemView(parent.context)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = items[position]

        holder.customView.bind(item.description, item.date, item.imageUrl)

        holder.customView.imageView.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelable("searchItem", item)

            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle

            val fragmentManager = (it.context as AppCompatActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, detailFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    override fun getItemCount(): Int = items.size
}
