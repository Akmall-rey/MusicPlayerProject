package com.example.musicplayerproject.ui.fragments.home.explore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayerproject.R
import com.example.musicplayerproject.response.TrackItem

class MusicAdapter(
    private val context: Context,
    private val data: ArrayList<TrackItem>
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val songTitle: TextView = view.findViewById(R.id.song_title)
        val songSinger: TextView = view.findViewById(R.id.song_singer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.search_result_recyclerview, parent, false)
        return MusicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val trackItem = data[position]
        holder.songTitle.text = trackItem.strTrack
        holder.songSinger.text = trackItem.strArtist

        
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData: ArrayList<TrackItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}
