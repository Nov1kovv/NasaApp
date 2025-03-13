package com.example.nasaapp.ui.search.details

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private var mediaUrl: String = ""

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

        val mediaItem = MediaItem.fromUri(Uri.parse(""))
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        binding.playerView.player = exoPlayer

        imageProgressBar.visibility = View.VISIBLE
        Glide.with(requireContext())
            .load(formatImageUrl(imageUrl))
            .centerCrop()
            .into(binding.imageView)

        binding.imageView.post{
            imageProgressBar.visibility = View.GONE
        }

        if (nasaId.isNotEmpty()) {
            fileInfoProgressBar.visibility = View.VISIBLE
            detailViewModel.fetchDetailedInfo(nasaId)
            detailViewModel.fetchVideoLink(nasaId)
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
        detailViewModel.videoLink.observe(viewLifecycleOwner) { videoLink ->
            if (videoLink.isNotEmpty()) {
                val fixedVideoLink = formatVideoUrl(videoLink)
                Log.d("DetailFragment", "Video link received: $fixedVideoLink")
                binding.imageView.visibility = View.GONE
                updatePlayerWithVideo(fixedVideoLink)
                mediaUrl = fixedVideoLink
            } else {
                binding.playerView.visibility = View.GONE
                mediaUrl = imageUrl
                Log.e("DetailFragment", "Video link is empty or unavailable.")
            }
        }

        binding.downloadButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            } else {
                downloadContent(mediaUrl)
            }
        }

        binding.shareButton.setOnClickListener {
            shareContent(mediaUrl)
        }

        return binding.root
    }

    private fun formatVideoUrl(videoLink: String): String {
        return videoLink.replace(" ", "%20")
            .replace("+", "%2B")
            .replace("http://", "https://")
    }

    private fun formatImageUrl(imageUrl: String): String {
        return imageUrl.replace(" ", "%20")
            .replace("+", "%2B")
            .replace("http://", "https://")
    }

    private fun updatePlayerWithVideo(videoLink: String) {
        if (videoLink.isNotEmpty()) {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoLink))
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.prepare()
            exoPlayer?.playWhenReady = true
            Log.d("DetailFragment", "Playing video from URL: $videoLink")
        } else {
            Log.e("DetailFragment", "No video link available to play")
        }
    }

    private fun downloadContent(url: String) {
        if (url.isEmpty()) {
            Log.e("DetailFragment", "No content to download")
            return
        }

        val uri = Uri.parse(url)
        val fileName = uri.lastPathSegment ?: "downloaded_file"

        val request = DownloadManager.Request(uri)
            .setTitle(fileName)
            .setDescription("Downloading file")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(requireContext(), "Downloading $fileName", Toast.LENGTH_SHORT).show()
    }

    private fun shareContent(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(intent, "Поделиться через"))
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
