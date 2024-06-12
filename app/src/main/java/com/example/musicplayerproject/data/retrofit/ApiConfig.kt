package com.example.musicplayerproject.network

import com.example.musicplayerproject.response.MusicResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//import com.example.musicplayerproject.response.MusicResponse
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET

interface ApiService {
    @GET("https://theaudiodb.com/api/v1/json/2/track.php?m=2115888")
    fun getMusic(): Call<MusicResponse>
}

object ApiConfig {
    private const val BASE_URL = "https://www.theaudiodb.com/"

    fun apiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
