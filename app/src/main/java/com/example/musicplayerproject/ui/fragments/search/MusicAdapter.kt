package com.example.musicplayerproject.ui.fragments.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.databinding.SearchResultRecyclerviewBinding

class MusicAdapter(private val listMusic: ArrayList<MusicFiles>) :
    RecyclerView.Adapter<MusicAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(val binding: SearchResultRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            SearchResultRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listMusic.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val musicFile = listMusic[position]
        with(holder.binding) {
            songTitle.text = musicFile.getTitle()
            songSinger.text = musicFile.getArtist()

        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listMusic[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MusicFiles)
    }
}
