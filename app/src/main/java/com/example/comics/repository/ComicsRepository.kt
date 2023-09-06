package com.example.comics.repository

import com.example.comics.data.remote.Api
import com.example.comics.model.ItemModel

class ComicsRepository(private val service: Api) {
    suspend fun getComics(): ItemModel = service.getComics(
        apikey = "b7e14bab409c70a5c4e7c2b319c09d7b",
        ts = "1682982412",
        hash = "3482f01e9bf207a437a4b621c91339ad"
    )
}