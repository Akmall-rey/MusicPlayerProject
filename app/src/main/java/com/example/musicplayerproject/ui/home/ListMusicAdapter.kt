package com.example.musicplayerproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.R

class ListMusicAdapter(private val listRelic: ArrayList<Music>) : RecyclerView.Adapter<ListMusicAdapter.ListViewHolder>() {
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

    override fun getItemCount(): Int = listRelic.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (img) = listRelic[position]
        holder.imgPhoto.setImageResource(img)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listRelic[holder.adapterPosition])}
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Music)
    }
}