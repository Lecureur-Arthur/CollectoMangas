package com.example.collectomangas.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collectomangas.ui.components.MangaCard
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(modifier: Modifier = Modifier) {
    var showComingSoon by remember { mutableStateOf(false) }
    val db = FirebaseFirestore.getInstance()

    // State pour stocker la liste récupérée depuis Firestore
    var mangaTitles by remember { mutableStateOf(listOf<String>()) }

    // Charger les titres depuis Firestore une seule fois au lancement du composable
    LaunchedEffect(Unit) {
        db.collection("mangas")
            .get()
            .addOnSuccessListener { result ->
                val titlesFromDb = result.documents.mapNotNull { it.getString("title") }
                mangaTitles = titlesFromDb
            }
            .addOnFailureListener {
                // Ici tu peux gérer l'erreur, par ex. afficher un message ou log
                mangaTitles = listOf()
            }
    }

    if (showComingSoon) {
        ComingSoonScreen(
            onBack = { showComingSoon = false }  // Ajouter ce paramètre dans ta composable existante si besoin
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Ma Collection") })
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .fillMaxSize()
            ) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(bottom = 100.dp)
                ) {
                    mangaTitles.forEach { title ->
                        MangaCard(title = title)
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .padding(bottom = 80.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            showComingSoon = true
                        },
                        modifier = Modifier
                            .height(56.dp)
                            .width(140.dp)
                    ) {
                        Icon(Icons.Filled.QrCodeScanner, contentDescription = "Scanner")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Scanner")
                    }

                    Button(
                        onClick = { /* Action ajouter */ },
                        modifier = Modifier
                            .height(56.dp)
                            .width(140.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Ajouter")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ajouter")
                    }
                }
            }
        }
    }
}