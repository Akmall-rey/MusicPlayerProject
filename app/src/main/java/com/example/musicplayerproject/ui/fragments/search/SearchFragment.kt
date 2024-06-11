package com.example.musicplayerproject.ui.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.data.repositories.MusicRepository
import com.example.musicplayerproject.databinding.FragmentSearchBinding
import com.example.musicplayerproject.ui.shared.SharedViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var musicRepository: MusicRepository
    private lateinit var musicFiles: ArrayList<MusicFiles>
    private lateinit var filteredMusicFiles: ArrayList<MusicFiles>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the repository
        musicRepository = MusicRepository()

        // Initialize the music files lists
        musicFiles = arrayListOf()
        filteredMusicFiles = arrayListOf()

        // Set up the RecyclerView
        setupRecyclerView()

        // Add TextWatcher to EditText for search functionality
        binding.searchMusic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                handleSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed
            }
        })

        // Load music files
        loadMusicFiles()

        return root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvMusic
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        musicAdapter = MusicAdapter(filteredMusicFiles)
        recyclerView.adapter = musicAdapter

        musicAdapter.setOnItemClickCallback(object : MusicAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MusicFiles) {
                // Handle item click
            }
        })
    }

    private fun handleSearch(query: String) {
        filteredMusicFiles.clear()
        if (query.isEmpty()) {
            filteredMusicFiles.addAll(musicFiles)
        } else {
            for (music in musicFiles) {
                if (music.getTitle().contains(query, ignoreCase = true)) {
                    filteredMusicFiles.add(music)
                }
            }
        }
        musicAdapter.notifyDataSetChanged()
    }

    private fun loadMusicFiles() {
        // Call repository method to get all audio files
        musicFiles.clear()
        musicFiles.addAll(musicRepository.getAllAudio(requireContext()))
        // Initially display all music files
        filteredMusicFiles.clear()
        filteredMusicFiles.addAll(musicFiles)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
