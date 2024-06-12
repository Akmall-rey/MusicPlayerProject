package com.example.musicplayerproject.ui.fragments.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.R
import com.example.musicplayerproject.data.models.Music
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.data.models.MusicPlaylist
import com.example.musicplayerproject.data.models.Playlist
import com.example.musicplayerproject.data.models.setDialogBtnBackground
import com.example.musicplayerproject.data.repositories.MusicRepository
import com.example.musicplayerproject.databinding.AddPlaylistDialogBinding
import com.example.musicplayerproject.databinding.FragmentHomeBinding
import com.example.musicplayerproject.ui.fragments.profile.EditProfile
import com.example.musicplayerproject.ui.fragments.profile.ProfileFragment
import com.example.musicplayerproject.ui.fragments.profile.SettingPreferences
import com.example.musicplayerproject.ui.fragments.profile.ViewModelFactory
import com.example.musicplayerproject.ui.fragments.profile.dataStore
import com.example.musicplayerproject.ui.fragments.search.MusicAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var rvExplore: RecyclerView
    private lateinit var rvPlaylist: RecyclerView
    private lateinit var rvSongs: RecyclerView

    private lateinit var listSongAdapter: ListSongAdapter
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var musicRepository: MusicRepository
    private lateinit var musicFiles: ArrayList<MusicFiles>
    private lateinit var filteredMusicFiles: ArrayList<MusicFiles>
    private lateinit var adapter: ListPlaylistAdapter

//    private lateinit var listPlaylistAdapter: ListPlaylistAdapter

    // Daftar musik yang dijelajahi dan playlist
    private val listExplore = ArrayList<Music>()
//    private val listPlaylist = ArrayList<Playlist>()

    // Binding untuk layout FragmentHomeBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Broadcast Receiver untuk update username dan gambar profil
    private lateinit var usernameReceiver: BroadcastReceiver
    private lateinit var profileImageReceiver: BroadcastReceiver

    companion object {
        const val ACTION_UPDATE_PROFILE_IMAGE = "com.example.UPDATE_PROFILE_IMAGE"
        const val PROFILE_IMAGE_FILENAME = "profile_image.png"
        const val KEY_USERNAME = "username"
        var musicPlaylist: MusicPlaylist = MusicPlaylist()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        musicRepository = MusicRepository()

        // Initialize the music files lists
        musicFiles = arrayListOf()

        setupRecyclerViewSongs()
        loadMusicFiles()

        return root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPlaylist.setHasFixedSize(true)
        binding.rvPlaylist.setItemViewCacheSize(13)
        binding.rvPlaylist.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ListPlaylistAdapter(requireContext(), playlistList = musicPlaylist.ref)
        binding.rvPlaylist.adapter = adapter

        if (musicPlaylist.ref.isNotEmpty()) binding.instructionPA.visibility = View.GONE
        // Initialize adapter and set it to RecyclerView

//        adapter = ListPlaylistAdapter(requireContext(), listPlaylist)
//        binding.rvPlaylist.adapter = adapter
//        binding.rvPlaylist.layoutManager = LinearLayoutManager(requireContext())

        // Set up addPlaylist button listener
        binding.addPlaylistBtn.setOnClickListener { customAlertDialog() }

        // Inisialisasi RecyclerView untuk daftar musik yang dijelajahi dan playlist
        rvExplore = view.findViewById(R.id.rv_explore)
        setupRecyclerViewExplore(rvExplore, listExplore)

        rvSongs = view.findViewById(R.id.rv_songs)

        // Mengambil daftar musik dan playlist
        listExplore.addAll(getListMusic())
//        listPlaylist.addAll(getListPlaylist())

        // Refresh adapter's playlist
        adapter.refreshPlaylist()

        // Inisialisasi ViewModel dengan factory
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val factory = ViewModelFactory(pref)
        val homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        // Implementasi pengaturan tema
        homeViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Mengambil username dari SharedPreferences
        val savedUsername = getUsername()
        // Menetapkan teks TextView dengan username yang didapatkan dari SharedPreferences
        binding.username.text = savedUsername ?: getString(R.string.username)

        // Menambahkan broadcast receiver untuk pembaruan username
        usernameReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == EditProfile.ACTION_UPDATE_USERNAME) {
                    val newUsername = intent.getStringExtra(ProfileFragment.KEY_USERNAME)
                    binding.username.text = newUsername
                }
            }
        }
        val filter = IntentFilter(EditProfile.ACTION_UPDATE_USERNAME)
        requireContext().registerReceiver(usernameReceiver, filter, Context.RECEIVER_NOT_EXPORTED)

        // Menambahkan broadcast receiver untuk gambar profil
        profileImageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("HomeFragment", "Broadcast received for profile image update")
                loadProfileImage()
            }
        }
        val profileImageFilter = IntentFilter(ACTION_UPDATE_PROFILE_IMAGE)
        requireContext().registerReceiver(profileImageReceiver, profileImageFilter,
            Context.RECEIVER_NOT_EXPORTED)

        // Memuat gambar profil saat fragment dimulai
        loadProfileImage()
    }

    private fun customAlertDialog() {
        val customDialog = LayoutInflater.from(requireContext()).inflate(R.layout.add_playlist_dialog, binding.root, false)
        val binder = AddPlaylistDialogBinding.bind(customDialog)
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialog = builder.setView(customDialog)
            .setTitle("Playlist Details")
            .setPositiveButton("ADD") { dialog, _ ->
                val playlistName = binder.playlistName.text
                if (playlistName != null)
                    if (playlistName.isNotEmpty()) {
                        addPlaylist(playlistName.toString())
                    }
                dialog.dismiss()
            }.create()
        dialog.show()
        setDialogBtnBackground(requireContext(), dialog)
    }

    private fun addPlaylist(name: String) {
        var playlistExists = false
        for (i in musicPlaylist.ref) {
            if (name == i.name) {
                playlistExists = true
                break
            }
        }
        if (playlistExists) {
            Toast.makeText(requireContext(), "Playlist Exist!!", Toast.LENGTH_SHORT).show()
        } else {
            val tempPlaylist = Playlist()
            tempPlaylist.name = name
            tempPlaylist.playlist = ArrayList()
            musicPlaylist.ref.add(tempPlaylist)
            adapter.refreshPlaylist()
        }
    }

    private fun setupRecyclerViewExplore(recyclerView: RecyclerView, list: ArrayList<Music>) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val listMusicAdapter = ListMusicAdapter(list)
        recyclerView.adapter = listMusicAdapter

        listMusicAdapter.setOnItemClickCallback(object : ListMusicAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Music) {
                showSelectedMusic(data)
            }
        })
    }

    private fun setupRecyclerViewSongs() {
        val recyclerView = binding.rvSongs
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        listSongAdapter = ListSongAdapter(musicFiles)
        recyclerView.adapter = listSongAdapter

        listSongAdapter.setOnItemClickCallback(object : ListSongAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MusicFiles) {
                Toast.makeText(requireContext(), "is selected music", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getListMusic(): ArrayList<Music> {
        val dataImg = resources.obtainTypedArray(R.array.music_img)
        val listMusic = ArrayList<Music>()
        try {
            for (i in 0 until dataImg.length()) {
                val music = Music(dataImg.getResourceId(i, -1))
                listMusic.add(music)
            }
        } finally {
            dataImg.recycle()
        }
        return listMusic
    }

//    private fun getListPlaylist(): ArrayList<Playlist> {
//        val dataImg = resources.obtainTypedArray(R.array.playlist_img)
//        val listPlaylist = ArrayList<Playlist>()
//        try {
//            for (i in 0 until dataImg.length()) {
//                val playlist = Playlist(dataImg.getResourceId(i, -1))
//                listPlaylist.add(playlist)
//            }
//        } finally {
//            dataImg.recycle()
//        }
//        return listPlaylist
//    }

    private fun showSelectedMusic(music: Music) {
        context?.let {
            Toast.makeText(it, "is selected music", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_homeFragment_to_exploreFragment)
        }
    }

//    private fun showSelectedPlaylist(playlist: Playlist) {
//        context?.let {
//            Toast.makeText(it, "is selected playlist", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_homeFragment_to_playlistFragment)
//        }
//    }

    private fun getUsername(): String? {
        val sharedPref =
            requireContext().getSharedPreferences(ProfileFragment.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(ProfileFragment.KEY_USERNAME, null)
    }

    private fun loadProfileImage() {
        Log.d("HomeFragment", "Loading profile image")
        val bitmap = loadImageFromInternalStorage(PROFILE_IMAGE_FILENAME)
        bitmap?.let {
            Log.d("HomeFragment", "Profile image loaded")
            binding.profileImg.setImageBitmap(it)
        } ?: Log.d("HomeFragment", "Failed to load profile image")
    }

    private fun loadImageFromInternalStorage(filename: String): Bitmap? {
        return try {
            requireContext().openFileInput(filename).use { fis ->
                BitmapFactory.decodeStream(fis)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun loadMusicFiles() {
        musicFiles.clear()
        musicFiles.addAll(musicRepository.getAllAudio(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(usernameReceiver)
        requireContext().unregisterReceiver(profileImageReceiver)
        _binding = null
    }
}
