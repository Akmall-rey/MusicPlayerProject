package com.example.musicplayerproject.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.databinding.ItemSongBinding
import com.example.musicplayerproject.ui.fragments.search.MusicAdapter

class ListSongAdapter (private val listMusic: ArrayList<MusicFiles>) :
    RecyclerView.Adapter<ListSongAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback:OnItemClickCallback) {
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
            title.text = musicFile.getTitle()

        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listMusic[holder.adapterPosition])
        }
    }


    interface OnItemClickCallback : MusicAdapter.OnItemClickCallback {
        override fun onItemClicked(data: MusicFiles)
    }
}