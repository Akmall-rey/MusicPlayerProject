package com.example.musicplayerproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.R
import com.example.musicplayerproject.databinding.FragmentHomeBinding
import com.example.musicplayerproject.ui.profile.SettingPreferences
import com.example.musicplayerproject.ui.profile.ViewModelFactory
import com.example.musicplayerproject.ui.profile.dataStore

class HomeFragment : Fragment() {

    private lateinit var rvExplore: RecyclerView
    private lateinit var rvPlaylist: RecyclerView

    private val listExplore = ArrayList<Music>()
    private val listPlaylist = ArrayList<Playlist>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setting up ViewModel with Factory
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val factory = ViewModelFactory(pref)
        val homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        // Theme settings implementation
        homeViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvExplore = view.findViewById(R.id.rv_explore)
        setupRecyclerViewExplore(rvExplore, listExplore)

        rvPlaylist = view.findViewById(R.id.rv_playlist)
        setupRecyclerViewPlaylist(rvPlaylist, listPlaylist)

        listExplore.addAll(getListMusic())
        listPlaylist.addAll(getListPlaylist())
    }

    private fun setupRecyclerViewExplore(recyclerView: RecyclerView, list: ArrayList<Music>) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val listMusicAdapter = ListMusicAdapter(list)
        recyclerView.adapter = listMusicAdapter

        listMusicAdapter.setOnItemClickCallback(object : ListMusicAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Music) {
                showSelectedMusic(data)
            }
        })
    }

    private fun setupRecyclerViewPlaylist(recyclerView: RecyclerView, list: ArrayList<Playlist>) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val listPlaylistAdapter = ListPlaylistAdapter(list)
        recyclerView.adapter = listPlaylistAdapter

        listPlaylistAdapter.setOnItemClickCallback(object : ListPlaylistAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Playlist) {
                showSelectedPlaylist(data)
            }
        })
    }

    private fun getListMusic(): ArrayList<Music> {
        val dataImg = resources.obtainTypedArray(R.array.music_img)
        val listHero = ArrayList<Music>()
        try {
            for (i in 0 until dataImg.length()) {
                val hero = Music(dataImg.getResourceId(i, -1))
                listHero.add(hero)
            }
        } finally {
            dataImg.recycle()
        }
        return listHero
    }

    private fun getListPlaylist(): ArrayList<Playlist> {
        val dataImg = resources.obtainTypedArray(R.array.playlist_img)
        val listHero = ArrayList<Playlist>()
        try {
            for (i in 0 until dataImg.length()) {
                val hero = Playlist(dataImg.getResourceId(i, -1))
                listHero.add(hero)
            }
        } finally {
            dataImg.recycle()
        }
        return listHero
    }

    private fun showSelectedMusic(music: Music) {
        context?.let {
            Toast.makeText(it, "is selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSelectedPlaylist(playlist: Playlist) {
        context?.let {
            Toast.makeText(it, "is selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
