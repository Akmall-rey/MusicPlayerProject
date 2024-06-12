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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.R
import com.example.musicplayerproject.data.models.Music
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.data.repositories.MusicRepository
import com.example.musicplayerproject.databinding.FragmentHomeBinding
import com.example.musicplayerproject.ui.fragments.profile.EditProfile
import com.example.musicplayerproject.ui.fragments.profile.ProfileFragment
import com.example.musicplayerproject.ui.fragments.profile.SettingPreferences
import com.example.musicplayerproject.ui.fragments.profile.ViewModelFactory
import com.example.musicplayerproject.ui.fragments.profile.dataStore
import com.example.musicplayerproject.ui.fragments.search.MusicAdapter
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var rvExplore: RecyclerView
    private lateinit var rvPlaylist: RecyclerView

    private lateinit var musicAdapter: MusicAdapter
    private lateinit var musicRepository: MusicRepository
    private lateinit var musicFiles: ArrayList<MusicFiles>
    private lateinit var filteredMusicFiles: ArrayList<MusicFiles>

    // Daftar musik yang dijelajahi dan playlist
    private val listExplore = ArrayList<Music>()
    private val listPlaylist = ArrayList<Playlist>()

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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi RecyclerView untuk daftar musik yang dijelajahi dan playlist
        rvExplore = view.findViewById(R.id.rv_explore)
        setupRecyclerViewExplore(rvExplore, listExplore)

        rvPlaylist = view.findViewById(R.id.rv_playlist)
        setupRecyclerViewPlaylist(rvPlaylist, listPlaylist)

        // Mengambil daftar musik dan playlist dari sumber daya
        listExplore.addAll(getListMusic())
        listPlaylist.addAll(getListPlaylist())

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
        requireContext().registerReceiver(
            profileImageReceiver, profileImageFilter,
            Context.RECEIVER_NOT_EXPORTED
        )

        // Memuat gambar profil saat fragment dimulai
        loadProfileImage()
    }

    // Fungsi untuk mengatur RecyclerView untuk daftar musik yang dijelajahi
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

    // Fungsi untuk mengatur RecyclerView untuk playlist
    private fun setupRecyclerViewPlaylist(recyclerView: RecyclerView, list: ArrayList<Playlist>) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val listPlaylistAdapter = ListPlaylistAdapter(list)
        recyclerView.adapter = listPlaylistAdapter

        listPlaylistAdapter.setOnItemClickCallback(object :
            ListPlaylistAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Playlist) {
                showSelectedPlaylist(data)
            }
        })
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

    // Fungsi untuk mengambil daftar musik dari sumber daya
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

    // Fungsi untuk mengambil playlist dari sumber daya
    private fun getListPlaylist(): ArrayList<Playlist> {
        val dataImg = resources.obtainTypedArray(R.array.playlist_img)
        val listPlaylist = ArrayList<Playlist>()
        try {
            for (i in 0 until dataImg.length()) {
                val playlist = Playlist(dataImg.getResourceId(i, -1))
                listPlaylist.add(playlist)
            }
        } finally {
            dataImg.recycle()
        }
        return listPlaylist
    }

    // Fungsi yang dipanggil saat item musik dipilih
    private fun showSelectedMusic(music: Music) {
        context?.let {
            Toast.makeText(it, "is selected music", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_homeFragment_to_exploreFragment)
        }
    }

    // Fungsi yang dipanggil saat item playlist dipilih
    private fun showSelectedPlaylist(playlist: Playlist) {
        context?.let {
            Toast.makeText(it, "is selected playlist", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_homeFragment_to_playlistFragment)

        }
    }

    // Fungsi untuk mengambil username dari SharedPreferences
    private fun getUsername(): String? {
        val sharedPref =
            requireContext().getSharedPreferences(ProfileFragment.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(ProfileFragment.KEY_USERNAME, null)
    }

    // Fungsi untuk memuat gambar profil
    private fun loadProfileImage() {
        Log.d("HomeFragment", "Loading profile image")
        // Memuat gambar profil dari File Storage
        val bitmap = loadImageFromInternalStorage(PROFILE_IMAGE_FILENAME)
        bitmap?.let {
            Log.d("HomeFragment", "Profile image loaded")
            binding.profileImg.setImageBitmap(it)
        } ?: Log.d("HomeFragment", "Failed to load profile image")
    }

    // Fungsi untuk memuat gambar dari penyimpanan internal aplikasi.
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

//    private fun loadMusicFiles() {
//        // Call repository method to get all audio files
//        musicFiles.clear()
//        musicFiles.addAll(musicRepository.getAllAudio(requireContext()))
//        // Initially display all music files
//        filteredMusicFiles.clear()
//        filteredMusicFiles.addAll(musicFiles)
//    }

    // Fungsi untuk membersihkan resource yang tidak lagi dibutuhkan
    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(usernameReceiver)
        requireContext().unregisterReceiver(profileImageReceiver) // Unregister profile image receiver
        _binding = null
    }
}
