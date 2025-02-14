package com.example.nasaapp.ui.search.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaapp.databinding.DetailFragmentBinding
import com.example.nasaapp.domain.model.SearchItem

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchItem: SearchItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val searchItemId = arguments?.getString("searchItemId")
            ?: throw IllegalArgumentException("SearchItem ID argument is required")


        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        with(binding) {
            fileSize.text = "File Size: 500MB"
            fileFormat.text = "Format: MP4"
            nasaId.text = "NASA ID: ABC123XYZ"


        downloadButton.setOnClickListener {

        }
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



