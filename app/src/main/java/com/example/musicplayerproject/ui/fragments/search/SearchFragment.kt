package com.example.musicplayerproject.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.databinding.FragmentSearchBinding
import com.example.musicplayerproject.ui.shared.SharedViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        showRecyclerView()

        return root
    }

    private fun showRecyclerView() {
        val recyclerView = binding.rvMusic
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sharedViewModel.musicFiles.observe(viewLifecycleOwner) { musicList ->
            val musicAdapter = MusicAdapter(ArrayList(musicList))
            recyclerView.adapter = musicAdapter

            musicAdapter.setOnItemClickCallback(object : MusicAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MusicFiles) {
                    // Handle item click
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
