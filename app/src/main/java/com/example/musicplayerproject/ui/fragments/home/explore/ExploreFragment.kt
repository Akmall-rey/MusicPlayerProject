package com.example.musicplayerproject.ui.fragments.home.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayerproject.R
import com.example.musicplayerproject.network.ApiConfig
import com.example.musicplayerproject.response.AlbumResponse
import com.example.musicplayerproject.response.MusicResponse
import com.example.musicplayerproject.response.TrackItem
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment : Fragment() {

    private lateinit var albumTitleTextView: TextView
    private lateinit var artistNameTextView: TextView
    private lateinit var albumImageView: ImageView
    private lateinit var descriptionTextView: TextView
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        albumTitleTextView = view.findViewById(R.id.album_title)
        artistNameTextView = view.findViewById(R.id.artist_name)
        albumImageView = view.findViewById(R.id.album_image)
        descriptionTextView = view.findViewById(R.id.album_description)
        recyclerView = view.findViewById(R.id.music_explore_recyclerview)
        val toolbar: MaterialToolbar = view.findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter and RecyclerView
        musicAdapter = MusicAdapter(requireContext(), arrayListOf())
        recyclerView.adapter = musicAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // Fetch and set data
        getAlbumDetails("2115888") // Replace with actual album id
    }

    private fun getAlbumDetails(albumId: String) {
        ApiConfig.apiService().getAlbumDetails(albumId).enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>, response: Response<AlbumResponse>) {
                if (response.isSuccessful) {
                    val album = response.body()?.album?.firstOrNull()
                    album?.let {
                        albumTitleTextView.text = it.strAlbum
                        artistNameTextView.text = it.strArtist
                        descriptionTextView.text = it.strDescriptionEN
                        Glide.with(requireContext())
                            .load(it.strAlbumThumb)
                            .into(albumImageView)

                        it.idAlbum?.let { it1 -> getTracks(it1) }
                    }
                }
            }

            override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun getTracks(albumId: String) {
        ApiConfig.apiService().getTracks(albumId).enqueue(object : Callback<MusicResponse> {
            override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.track
                    data?.let {
                        musicAdapter.setData(it as ArrayList<TrackItem>)
                    }
                }
            }

            override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}
