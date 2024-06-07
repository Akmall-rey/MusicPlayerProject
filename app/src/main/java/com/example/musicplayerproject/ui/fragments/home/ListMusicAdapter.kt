package com.example.musicplayerproject.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.R
import com.example.musicplayerproject.data.models.Music

class ListMusicAdapter(private val listMusic: ArrayList<Music>) : RecyclerView.Adapter<ListMusicAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_music_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_music, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listMusic.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (img) = listMusic[position]
        holder.imgPhoto.setImageResource(img)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMusic[holder.adapterPosition])}
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Music)
    }
}