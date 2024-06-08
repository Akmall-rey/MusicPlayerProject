package com.example.musicplayerproject.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(
    val img: Int
) : Parcelable
