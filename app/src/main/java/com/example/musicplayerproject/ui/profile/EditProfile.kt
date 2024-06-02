package com.example.musicplayerproject.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.musicplayerproject.R
import com.example.musicplayerproject.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEditProfileBinding

    companion object {
        const val EXTRA_TYPE_FORM = "extra_type_form"
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101

        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
        private const val FIELD_IS_NOT_VALID = "Email tidak valid"

        const val PREF_NAME = "MyPrefs"
        private const val KEY_USERNAME = "username"
        const val KEY_PROFILE_IMAGE_URI = "profile_image_uri"

        const val ACTION_UPDATE_USERNAME = "com.example.roxy.UPDATE_USERNAME"
    }

    private lateinit var userModel: User
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 200

    private val pickImageResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                handleImageSelection(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener(this)
        binding.btnChooseImage.setOnClickListener {
            checkAndRequestPermissions()
        }

        userModel = intent.getParcelableExtra<User>("USER") as User
        val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)

        var actionBarTitle = ""
        var btnTitle = ""

        when (formType) {
            TYPE_ADD -> {
                actionBarTitle = "Tambah Baru"
                btnTitle = "Simpan"
            }
            TYPE_EDIT -> {
                actionBarTitle = "Ubah"
                btnTitle = "Update"
                showPreferenceInForm()
            }
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.text = btnTitle

        binding.edtUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val username = s.toString()
                binding.username.text = username
                saveUsername(username)
                sendUsernameBroadcast(username)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val savedUsername = getUsername()
        binding.username.text = savedUsername ?: getString(R.string.username)

        val savedImageUri = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString(KEY_PROFILE_IMAGE_URI, null)
        savedImageUri?.let {
            try {
                val inputStream = contentResolver.openInputStream(Uri.parse(it))
                val bitmap = BitmapFactory.decodeStream(inputStream)
                binding.profileImg.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
        } else {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageResultLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleImageSelection(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.profileImg.setImageBitmap(bitmap)

            // Simpan URI gambar ke SharedPreferences
            getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit {
                putString(KEY_PROFILE_IMAGE_URI, uri.toString())
                apply()
            }

            // Kirim siaran bahwa gambar profil telah diubah
            sendBroadcast(Intent(ProfileFragment.ACTION_UPDATE_PROFILE_IMAGE))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun saveUsername(username: String) {
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    private fun getUsername(): String? {
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY_USERNAME, null)
    }

    private fun sendUsernameBroadcast(username: String) {
        val intent = Intent(ACTION_UPDATE_USERNAME).apply {
            putExtra(KEY_USERNAME, username)
        }
        sendBroadcast(intent)
    }

    private fun showPreferenceInForm() {
        binding.edtUsername.setText(userModel.name)
        binding.edtEmail.setText(userModel.email)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_save) {
            val name = binding.edtUsername.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()

            if (name.isEmpty()) {
                binding.edtUsername.error = FIELD_REQUIRED
                return
            }

            if (email.isEmpty()) {
                binding.edtEmail.error = FIELD_REQUIRED
                return
            }

            if (!isValidEmail(email)) {
                binding.edtEmail.error = FIELD_IS_NOT_VALID
                return
            }

            saveUser(name, email)

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_RESULT, userModel)
            setResult(RESULT_CODE, resultIntent)

            finish()
        }
    }

    private fun saveUser(name: String, email: String) {
        val userPreference = UserPreference(this)

        userModel.name = name
        userModel.email = email

        userPreference.setUser(userModel)
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}