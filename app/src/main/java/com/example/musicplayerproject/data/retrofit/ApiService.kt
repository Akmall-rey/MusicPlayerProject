package com.example.musicplayerproject.data.retrofit

import com.example.musicplayerproject.response.AlbumResponse
import com.example.musicplayerproject.response.MusicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("track.php")
    fun getTracks(@Query("m") albumId: String): Call<MusicResponse>

    @GET("album.php")
    fun getAlbumDetails(@Query("m") albumId: String): Call<AlbumResponse>
}