package com.example.nasaapp.ui.search.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import android.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
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
    private var exoPlayer: ExoPlayer? = null

    @OptIn(UnstableApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val imageUrl = arguments?.getString("imageUrl") ?:""
        Log.d("DetailFragment", "Video URL: $imageUrl")
        val nasaId = arguments?.getString("nasaId") ?:""
        val description = arguments?.getString("description") ?: ""
        binding.fileDescriptionText.text = "Description: $description"
        imageProgressBar = binding.root.findViewById(R.id.imageProgressBar)
        fileInfoProgressBar = binding.root.findViewById(R.id.fileInfoProgressBar)

        exoPlayer = ExoPlayer.Builder(requireContext())
            .setTrackSelector(DefaultTrackSelector(requireContext()))
            .setLoadControl(DefaultLoadControl())
            .build()
        val mediaItem = MediaItem.fromUri(Uri.parse(imageUrl))
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        binding.playerView.player = exoPlayer

        imageProgressBar.visibility = View.VISIBLE
        Glide.with(requireContext())
            .load(imageUrl)
            .centerCrop()
            .into(binding.imageView)

        binding.imageView.post{
            imageProgressBar.visibility = View.GONE
        }

        if (nasaId.isNotEmpty()) {
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
    override fun onStart() {
        super.onStart()
        exoPlayer?.play()
    }
    override fun onStop() {
        super.onStop()
        exoPlayer?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
        exoPlayer = null
        _binding = null
    }
}
