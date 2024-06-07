package com.example.musicplayerproject.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var name: String? = null,
    var email: String? = null,
) : Parcelable