package com.example.nasaapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import com.example.nasaapp.domain.model.SearchItem

class DetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var fileSizeTextView: TextView
    private lateinit var fileFormatTextView: TextView
    private lateinit var nasaIdTextView: TextView
    private lateinit var downloadButton: Button
    private lateinit var searchItem: SearchItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchItem = arguments?.getParcelable("searchItem")!!
        val view = inflater.inflate(R.layout.detail_fragment, container, false)

        imageView = view.findViewById(R.id.image_view)
        fileSizeTextView = view.findViewById(R.id.file_size)
        fileFormatTextView = view.findViewById(R.id.file_format)
        nasaIdTextView = view.findViewById(R.id.nasa_id)
        downloadButton = view.findViewById(R.id.download_button)

        fileSizeTextView.text = "File Size: 500MB"
        fileFormatTextView.text = "Format: MP4"
        nasaIdTextView.text = "NASA ID: ABC123XYZ"

        val imageUrl = "https://imgpng.ru/img/heroes/spongebob/49737.png"
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)


        downloadButton.setOnClickListener {
//            downloadVideo("android.resource://${activity?.packageName}/${R.raw.why_does_the_moon_look_larger_at_the_horizon_we_asked_a_nasa_expert}")
        }

        return view
    }
}



