package com.example.musicplayerproject.ui.fragments.home.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.databinding.FragmentExploreBinding
import com.example.musicplayerproject.network.ApiConfig
import com.example.musicplayerproject.response.MusicResponse
import com.example.musicplayerproject.response.TrackItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter and RecyclerView
        musicAdapter = MusicAdapter(requireContext(), arrayListOf())
        recyclerView = binding.musicExploreRecyclerview
        recyclerView.adapter = musicAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // Fetch and set data
        getMusicData()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getMusicData() {
        ApiConfig.apiService().getMusic().enqueue(object : Callback<MusicResponse> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
