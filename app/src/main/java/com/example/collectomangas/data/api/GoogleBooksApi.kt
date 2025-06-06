package com.example.collectomangas.data.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object GoogleBooksApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getBookTitleByISBN(isbn: String): String? {
        val response: GoogleBooksResponse = client.get("https://www.googleapis.com/books/v1/volumes?q=isbn:$isbn").body()
        return response.items?.firstOrNull()?.volumeInfo?.title
    }
}

@Serializable
data class GoogleBooksResponse(
    val items: List<BookItem>?
)

@Serializable
data class BookItem(
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String
)
