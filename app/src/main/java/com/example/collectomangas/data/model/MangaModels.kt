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
        val titles: Map<String, String>? // nouveau champ pour les titres en plusieurs langues
    )


    data class PosterImage(
        val original: String
    )
