package com.example.musicplayerproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicplayerproject.databinding.ActivityMainBinding
import com.example.musicplayerproject.data.models.MusicFiles
import com.example.musicplayerproject.ui.fragments.search.MusicRepository
import com.example.musicplayerproject.ui.shared.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()


    companion object {
        private const val REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.hide()

        // Request permission
        requestPermission()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_AUDIO
                ),
                REQUEST_CODE
            )
        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            setUpMusicData()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            var allPermissionsGranted = false
            grantResults.forEachIndexed { index, result ->

                if (result == PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = true
                }
            }
            if (allPermissionsGranted) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                setUpMusicData()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAllMusic(): ArrayList<MusicFiles> {
        val musicRepository = MusicRepository()
        val musicFiles = musicRepository.getAllAudio(this)
        musicFiles.forEach { musicFile ->
            Log.d("MusicFile", "Path: ${musicFile.getPath()}, Title: ${musicFile.getTitle()}")
        }
        return musicFiles
    }

    private fun setUpMusicData() {
        val musicList = getAllMusic()
        sharedViewModel.setMusicFiles(musicList)
    }


}

