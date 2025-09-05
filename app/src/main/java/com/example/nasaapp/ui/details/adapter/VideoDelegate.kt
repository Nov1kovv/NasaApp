package com.example.nasaapp.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapp.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class VideoDelegate(
    private val onDownloadClick: (String) -> Unit,
    private val onShareClick: (String) -> Unit
) : AdapterDelegate<List<DetailItem>>() {

    override fun isForViewType(items: List<DetailItem>, position: Int): Boolean =
        items[position] is DetailItem.Video

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<DetailItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as DetailItem.Video
        (holder as VideoViewHolder).bind(item)
    }

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val playerView = view.findViewById<PlayerView>(R.id.playerView)
        private var exoPlayer: ExoPlayer? = null

        fun bind(item: DetailItem.Video) {
            exoPlayer = ExoPlayer.Builder(itemView.context).build().also {
                playerView.player = it
                val mediaItem = MediaItem.fromUri(item.videoUrl)
                it.setMediaItem(mediaItem)
                it.prepare()
                it.playWhenReady = true
            }
        }
    }
}