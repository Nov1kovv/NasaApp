package com.example.nasaapp.ui.details

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nasaapp.R
import com.example.nasaapp.data.mapper.DetailedDtoToDomainMapper
import com.example.nasaapp.databinding.DetailFragmentBinding
import com.example.nasaapp.ui.details.adapter.DetailAdapter
import com.example.nasaapp.ui.details.adapter.DetailItem
import com.example.nasaapp.ui.test.Contract
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var fileInfoProgressBar: ProgressBar
    private lateinit var adapter: DetailAdapter

    private var exoPlayer: ExoPlayer? = null
    private var mediaUrl: String = ""
    private val nasaId: String
        get() = arguments?.getString(ARG_NASA_ID) ?: ""

    @Inject
    lateinit var factory: DetailViewModel.Factory.DetailFactory

    @Inject
    lateinit var mapper2: DetailedDtoToDomainMapper //пример с Inject

    private lateinit var mapper1: DetailedDtoToDomainMapper //пример без Inject

    private val detailViewModel: DetailViewModel by viewModels {
        factory.create(nasaId)
    }

    override fun onAttach(context: Context) {
        val component = DaggerAppComponent.factory().create()
        mapper1 = component.provideDetailedDtoToDomainMapper()//пример без Inject
        component.inject(this)
        Log.i("fdskj.ha.", "onAttach: ${mapper1}")
        super.onAttach(context)
    }

    @OptIn(UnstableApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        adapter = DetailAdapter(
            onDownloadClick = { url -> downloadContent(url) },
            onShareClick = { url -> shareContent(url) }
        )
        binding.detailRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.detailRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf(
            DetailItem.TextItem("Text 1"),
            DetailItem.TextItem("Text 2"),
            DetailItem.TextItem("Text 3"),
            DetailItem.TextItem("Text 4"),
            DetailItem.TextItem("Text 5"),
            DetailItem.TextItem("Text 6"),
            DetailItem.TextItem("Text 7"),
            DetailItem.TextItem("Text 8"),
            DetailItem.TextItem("Text 9"),
            DetailItem.TextItem("Text 1"),
            DetailItem.TextItem("Text 2"),
            DetailItem.TextItem("Text 3"),
            DetailItem.TextItem("Text 4"),
            DetailItem.TextItem("Text 5"),
            DetailItem.TextItem("Text 6"),
            DetailItem.TextItem("Text 7"),
            DetailItem.TextItem("Text 8"),
            DetailItem.TextItem("Text 9"),
            DetailItem.TextItem("Text 1"),
            DetailItem.TextItem("Text 2"),
            DetailItem.TextItem("Text 3"),
            DetailItem.TextItem("Text 4"),
            DetailItem.TextItem("Text 5"),
            DetailItem.TextItem("Text 6"),
            DetailItem.Photo("https://assets.science.nasa.gov/dynamicimage/assets/science/astro/universe/internal_resources/402/Carina_Nebula-1.jpeg?w=1600&h=927&fit=clip&crop=faces%2Cfocalpoint"),
            DetailItem.TextItem("Text 7"),
            DetailItem.TextItem("Text 8"),
            DetailItem.TextItem("Text 9"),
            DetailItem.Video("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"),
            DetailItem.TextItem("Text 1"),
            DetailItem.TextItem("Text 2"),
            DetailItem.TextItem("Text 3"),
            DetailItem.TextItem("Text 4"),
            DetailItem.TextItem("Text 5"),
            DetailItem.TextItem("Text 6"),
            DetailItem.TextItem("Text 7"),
            DetailItem.TextItem("Text 8"),
            DetailItem.TextItem("Text 9")

            )
        adapter.items = items

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

        val downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager
        downloadManager?.enqueue(request)

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

    companion object {
        private const val ARG_NASA_ID = "nasaId"

        fun createDetailsFragment(nasaId: String): DetailFragment {
            return DetailFragment().apply {
                val bundle = Bundle()
                bundle.putString(ARG_NASA_ID, nasaId)
                arguments = bundle
            }
        }
    }
}
