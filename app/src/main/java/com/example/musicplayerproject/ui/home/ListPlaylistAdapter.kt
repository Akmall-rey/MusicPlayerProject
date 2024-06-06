package com.example.musicplayerproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.R

class ListPlaylistAdapter(private val listPlaylist: ArrayList<Playlist>) : RecyclerView.Adapter<ListPlaylistAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_playlist_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_playlist, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listPlaylist.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (img) = listPlaylist[position]
        holder.imgPhoto.setImageResource(img)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listPlaylist[holder.adapterPosition])}
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Playlist)
    }
}