package com.example.collectomangas.data.model

data class MangaResponse(
    val data: List<MangaData>
)

data class MangaData(
    val attributes: MangaAttributes
)

data class MangaAttributes(
    val canonicalTitle: String,
    val volumeCount: Int?,
    val posterImage: PosterImage,
    val titles: Map<String, String>?, // titres en plusieurs langues
    val synopsis: String?,            // synopsis du manga
    val status: String?,              // statut : current, finished, etc.
    val startDate: String?,           // date de début
    val endDate: String?              // date de fin
)

data class PosterImage(
    val original: String
)
