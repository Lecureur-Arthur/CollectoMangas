package com.example.collectomangas.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val kitsuService: KitsuApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://kitsu.io/api/edge/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KitsuApiService::class.java)
    }
}
