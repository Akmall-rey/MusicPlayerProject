package com.example.musicplayerproject.data.models

class MusicFiles {
    private val path: String
    private val title: String
    private val artist: String
    private val album: String
    private val duration: String

    constructor(path: String, title: String, artist: String, album: String, duration: String) {
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

    fun getDuration(): String {
        return duration;
    }


}