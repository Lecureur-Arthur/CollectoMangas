package com.example.collectomangas.data.api

import com.example.collectomangas.data.model.MangaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KitsuApiService {
    @GET("manga")
    suspend fun searchManga(@Query("filter[text]") title: String): MangaResponse
}
