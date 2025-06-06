package com.example.collectomangas.data

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseMangaRepository {
    private val db = FirebaseFirestore.getInstance()

    fun addManga(title: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val manga = hashMapOf(
            "title" to title,
            "addedAt" to System.currentTimeMillis()
        )

        db.collection("mangas")
            .add(manga)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
