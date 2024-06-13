package com.example.musicplayerproject.ui.fragments

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore

class musicPlayer {


    public fun startMusic(path:String, title:String)
    {
        var musicPlayer: MediaPlayer = MediaPlayer()
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        musicPlayer.setDataSource(path+title)
        musicPlayer.prepare()
        musicPlayer.start()
    }

    public fun pauseMusic(path:String, title:String)
    {
        var musicPlayer: MediaPlayer = MediaPlayer()
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        musicPlayer.setDataSource(path+title)
        musicPlayer.prepare()
        musicPlayer.pause()
    }

    public fun stopMusic(path:String, title:String)
    {
        var musicPlayer: MediaPlayer = MediaPlayer()
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        musicPlayer.setDataSource(path+title)
        musicPlayer.prepare()
        musicPlayer.stop()
    }
}
