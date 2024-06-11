package com.example.musicplayerproject.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.data.repositories.MusicRepository
import com.example.musicplayerproject.databinding.FragmentPlaylistBinding
import com.example.musicplayerproject.ui.shared.SharedViewModel

class PlaylistFragment : Fragment() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var listSongAdapter: ListSongAdapter
    private lateinit var musicRepository: MusicRepository
    private lateinit var musicFiles: ArrayList<MusicFiles>
    private lateinit var filteredMusicFiles: ArrayList<MusicFiles>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        // Initialize the repository
//        musicRepository = MusicRepository()
//
//        // Initialize the music files lists
//        musicFiles = arrayListOf()
//        filteredMusicFiles = arrayListOf()
//
//        // Set up the RecyclerView
//        setupRecyclerView()
//
//        loadMusicFiles()

        return root
    }

//    private fun setupRecyclerViewSong() {
//        val recyclerView = binding.fragmentAlbumContent.rvSongs
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        listSongAdapter = ListSongAdapter(filteredMusicFiles)
//        recyclerView.adapter = listSongAdapter
//
//        listSongAdapter.setOnItemClickCallback(object : ListSongAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: MusicFiles) {
//                // Handle item click
//            }
//        })
//    }
//
//    private fun loadMusicFiles() {
//        // Call repository method to get all audio files
//        musicFiles.clear()
//        musicFiles.addAll(musicRepository.getAllAudio(requireContext()))
//        // Initially display all music files
//        filteredMusicFiles.clear()
//        filteredMusicFiles.addAll(musicFiles)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
