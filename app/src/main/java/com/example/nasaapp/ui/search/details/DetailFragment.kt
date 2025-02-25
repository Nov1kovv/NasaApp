package com.example.nasaapp.ui.search.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import com.example.nasaapp.databinding.DetailFragmentBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var imageProgressBar: ProgressBar
    private lateinit var fileInfoProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val imageUrl = arguments?.getString("imageUrl") ?:""
        val nasaId = arguments?.getString("nasaId") ?:""
        val description = arguments?.getString("description") ?: ""
        binding.fileDescriptionText.text = "Description: $description"
        imageProgressBar = binding.root.findViewById(R.id.imageProgressBar)
        fileInfoProgressBar = binding.root.findViewById(R.id.fileInfoProgressBar)

        imageProgressBar.visibility = View.VISIBLE
        Glide.with(requireContext())
            .load(imageUrl)
            .centerCrop()
            .into(binding.imageView)

        Glide.with(requireContext())
            .load(imageUrl)
            .centerCrop()
            .into(binding.imageView)

        binding.imageView.post{
            imageProgressBar.visibility = View.GONE
        }

        if (nasaId != null) {
            fileInfoProgressBar.visibility = View.VISIBLE
            detailViewModel.fetchDetailedInfo(nasaId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.fileInfo.observe(viewLifecycleOwner) { detailedItem ->
                detailedItem?.let {
                    binding.fileSizeText.text = "File Size: ${it.fileSize}"
                    binding.fileFormatText.text = "Format: ${it.fileFormat}"
                    fileInfoProgressBar.visibility = View.GONE
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