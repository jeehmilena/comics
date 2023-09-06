package com.example.comics.data.remote

import com.example.comics.model.ItemModel
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("comics")
    suspend fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): ItemModel
}