package com.example.musicplayerproject.ui.fragments.home.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.R
import com.example.musicplayerproject.network.ApiConfig
import com.example.musicplayerproject.response.MusicResponse
import com.example.musicplayerproject.response.TrackItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ExploreFragment : Fragment() {

    private lateinit var musicAdapter: MusicAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var artistNameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        recyclerView = view.findViewById(R.id.music_explore_recyclerview)
        artistNameTextView = view.findViewById(R.id.artist_name)
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
        getMusicData()
    }

    private fun getMusicData() {
        ApiConfig.apiService().getMusic().enqueue(object : Callback<MusicResponse> {
            override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.track
                    data?.let {
                        musicAdapter.setData(it as ArrayList<TrackItem>)
                        if (it.isNotEmpty()) {
                            artistNameTextView.text =
                                it[0].strArtist // Set the artist name to the TextView
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}