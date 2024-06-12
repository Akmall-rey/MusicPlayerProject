package com.example.musicplayerproject.data.retrofit

import com.example.musicplayerproject.response.MusicResponse
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("users")
    fun getUsers(): Call<ArrayList<MusicResponse>>
}