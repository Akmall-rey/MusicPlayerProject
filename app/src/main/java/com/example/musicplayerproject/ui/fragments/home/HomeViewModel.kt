package com.example.musicplayerproject.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.musicplayerproject.ui.fragments.profile.SettingPreferences

class HomeViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    // Mengambil pengaturan tema dari preferensi pengaturan.
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}
