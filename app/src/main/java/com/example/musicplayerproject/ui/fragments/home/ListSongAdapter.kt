package com.example.musicplayerproject.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.databinding.ItemSongBinding
import com.example.musicplayerproject.ui.fragments.search.MusicAdapter

class ListSongAdapter(private val listMusic: ArrayList<MusicFiles>) :
    RecyclerView.Adapter<ListSongAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listMusic.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val musicFile = listMusic[position]
        with(holder.binding) {
            titleMusic.text = musicFile.getTitle()
            timeMusic.text = formatDuration(musicFile.getDuration())
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listMusic[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback : MusicAdapter.OnItemClickCallback {
        override fun onItemClicked(data: MusicFiles)
    }

    // Metode untuk mengonversi durasi
    private fun formatDuration(duration: Long): String {
        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
