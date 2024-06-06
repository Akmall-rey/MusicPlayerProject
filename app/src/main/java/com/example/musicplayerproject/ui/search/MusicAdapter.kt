package com.example.musicplayerproject.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.databinding.SearchResultRecyclerviewBinding

class SongAdapter(private val listSong: ArrayList<MusicFiles>) :
    RecyclerView.Adapter<SongAdapter.ListViewHolder>() {

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

    override fun getItemCount(): Int = listSong.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, singer, img) = listSong[position]
        with(holder.binding) {
            songCover.setImageResource(img)
            songTitle.text = title
            songSinger.text = singer
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listSong[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MusicFiles)
    }
}
