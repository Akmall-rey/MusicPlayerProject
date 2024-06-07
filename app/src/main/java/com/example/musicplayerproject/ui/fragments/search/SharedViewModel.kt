package com.example.musicplayerproject.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayerproject.data.models.MusicFiles

class SharedViewModel : ViewModel() {
    private val _musicFiles = MutableLiveData<ArrayList<MusicFiles>>()
    val musicFiles: LiveData<ArrayList<MusicFiles>> get() = _musicFiles

    fun setMusicFiles(musicList: ArrayList<MusicFiles>) {
        _musicFiles.value = musicList
    }
}
