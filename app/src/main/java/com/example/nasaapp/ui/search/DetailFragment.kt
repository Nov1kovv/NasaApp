package com.example.nasaapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.nasaapp.R
import com.example.nasaapp.domain.model.SearchItem

class DetailFragment : Fragment() {

    private lateinit var videoView: VideoView
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

        videoView = view.findViewById(R.id.video_view)
        fileSizeTextView = view.findViewById(R.id.file_size)
        fileFormatTextView = view.findViewById(R.id.file_format)
        nasaIdTextView = view.findViewById(R.id.nasa_id)
        downloadButton = view.findViewById(R.id.download_button)

        fileSizeTextView.text = "File Size: 500MB"
        fileFormatTextView.text = "Format: MP4"
        nasaIdTextView.text = "NASA ID: ABC123XYZ"


        downloadButton.setOnClickListener {

        }

        return view
    }
}