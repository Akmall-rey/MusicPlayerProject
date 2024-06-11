package com.example.musicplayerproject.ui.fragments.profile

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
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.IOException
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayerproject.R
import com.example.musicplayerproject.data.models.User
import com.example.musicplayerproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserPreference: UserPreference

    private var isPreferenceEmpty = false
    private lateinit var userModel: User
    private lateinit var usernameReceiver: BroadcastReceiver
    private lateinit var profileImageReceiver: BroadcastReceiver // Tambahkan receiver untuk gambar profil

    companion object {
        const val PREF_NAME = "MyPrefs"
        const val KEY_USERNAME = "username"
        const val ACTION_UPDATE_PROFILE_IMAGE = "com.example.roxy.UPDATE_PROFILE_IMAGE"
        const val PROFILE_IMAGE_FILENAME = "profile_image.png"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengambil username dari SharedPreferences
        val savedUsername = getUsername()
        // Mengatur teks TextView dengan username yang diambil dari SharedPreferences
        binding.username.text = savedUsername ?: getString(R.string.username)

        val themeButton: Button = binding.btnTheme
        val editButton: Button = binding.btnEdit

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val profileViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ProfileViewModel::class.java]

        profileViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeButton.isSelected = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeButton.isSelected = false
            }
        }

        userModel = User("", "")

        themeButton.setOnClickListener(this)
        editButton.setOnClickListener(this)

        // Register broadcast receiver
        usernameReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == EditProfile.ACTION_UPDATE_USERNAME) {
                    val newUsername = intent.getStringExtra(KEY_USERNAME)
                    binding.username.text = newUsername
                }
            }
        }
        val filter = IntentFilter(EditProfile.ACTION_UPDATE_USERNAME)
        requireContext().registerReceiver(usernameReceiver, filter, Context.RECEIVER_NOT_EXPORTED)

        // Register profile image receiver
        profileImageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("ProfileFragment", "Broadcast received for profile image update")
                loadProfileImage()
            }
        }
        val profileImageFilter = IntentFilter(ACTION_UPDATE_PROFILE_IMAGE)
        requireContext().registerReceiver(profileImageReceiver, profileImageFilter,
            Context.RECEIVER_NOT_EXPORTED)

        // Memuat gambar profil saat fragment dimulai
        loadProfileImage()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(usernameReceiver)
        requireContext().unregisterReceiver(profileImageReceiver) // Unregister profile image receiver
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_theme -> {
                val themeButton = view as Button
                val isChecked = !themeButton.isSelected
                themeButton.isSelected = isChecked

                val pref = SettingPreferences.getInstance(requireContext().dataStore)
                val profileViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ProfileViewModel::class.java]
                profileViewModel.saveThemeSetting(isChecked)

            }
            R.id.btn_edit -> {
                val intent = Intent(activity, EditProfile::class.java)
                intent.putExtra(EditProfile.EXTRA_TYPE_FORM, EditProfile.TYPE_EDIT)
                intent.putExtra("USER", userModel)
                startActivity(intent)
            }
        }
    }

    // Fungsi untuk mengambil username dari SharedPreferences
    private fun getUsername(): String? {
        val sharedPref = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY_USERNAME, null)
    }

    // Fungsi untuk memuat gambar profil
    private fun loadProfileImage() {
        Log.d("ProfileFragment", "Loading profile image")
        // Memuat gambar profil dari File Storage
        val bitmap = loadImageFromInternalStorage(PROFILE_IMAGE_FILENAME)
        bitmap?.let {
            Log.d("ProfileFragment", "Profile image loaded")
            binding.profileImg.setImageBitmap(it)
        } ?: Log.d("ProfileFragment", "Failed to load profile image")
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
}
