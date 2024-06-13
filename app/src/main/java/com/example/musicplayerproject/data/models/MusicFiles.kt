package com.example.musicplayerproject.data.models

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import com.example.musicplayerproject.R
import com.google.android.material.color.MaterialColors

class MusicFiles {
    private val path: String
    private val title: String
    private val artist: String
    private val album: String
    private val duration: Long

    constructor(path: String, title: String, artist: String, album: String, duration: Long) {
        this.path = path
        this.title = title
        this.artist = artist
        this.album = album
        this.duration = duration
    }

    fun getPath(): String {
        return path;
    }

    fun getTitle(): String {
        return title;
    }

    fun getArtist(): String {
        return artist;
    }

    fun getAlbum(): String {
        return album;
    }

    fun getDuration(): Long {
        return duration;
    }
}

class Playlist{
    lateinit var name: String
    lateinit var playlist: ArrayList<Music>
    lateinit var createdBy: String
    lateinit var createdOn: String
}
class MusicPlaylist{
    var ref: ArrayList<Playlist> = ArrayList()
}

fun setDialogBtnBackground(context: Context, dialog: AlertDialog){
    //setting button text
    dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)?.setTextColor(
        MaterialColors.getColor(context, R.attr.dialogTextColor, Color.WHITE)
    )
    dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)?.setTextColor(
        MaterialColors.getColor(context, R.attr.dialogTextColor, Color.WHITE)
    )

    //setting button background
    dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)?.setBackgroundColor(
        MaterialColors.getColor(context, R.attr.dialogBtnBackground, Color.RED)
    )
    dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)?.setBackgroundColor(
        MaterialColors.getColor(context, R.attr.dialogBtnBackground, Color.RED)
    )
}