package com.example.nasaapp.ui.search.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.nasaapp.databinding.DetailFragmentBinding


class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val imageUrl = arguments?.getString("imageUrl")
        val fileSize = arguments?.getString("fileSize")
        val fileFormat = arguments?.getString("fileFormat")
        val nasaId = arguments?.getString("nasaId")

        with(binding) {
            fileSizeText.text = "File Size: $fileSize"
            fileFormatText.text = "Format: $fileFormat"
            nasaIdText.text = "NASA ID: $nasaId"

            Glide.with(requireContext()).load(imageUrl).into(imageView)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}