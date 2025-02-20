package com.example.nasaapp.ui.search.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.nasaapp.databinding.DetailFragmentBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val imageUrl = arguments?.getString("imageUrl") ?:""
        val nasaId = arguments?.getString("nasaId") ?:""

        Glide.with(requireContext())
            .load(imageUrl)
            .centerCrop()
            .into(binding.imageView)

        if (nasaId != null) {
            detailViewModel.fetchDetailedInfo(nasaId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.fileInfo.observe(viewLifecycleOwner) { detailedItem ->
                detailedItem?.let {
                    binding.fileSizeText.text = "File Size: ${it.fileSize}"
                    binding.fileFormatText.text = "Format: ${it.fileFormat}"
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}