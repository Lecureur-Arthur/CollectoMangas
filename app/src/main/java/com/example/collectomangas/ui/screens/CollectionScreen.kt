package com.example.collectomangas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collectomangas.data.api.ApiClient
import com.example.collectomangas.ui.components.MangaCard
import com.example.collectomangas.ui.modal.AddMangaDialog
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(modifier: Modifier = Modifier) {
    val db = FirebaseFirestore.getInstance()

    var showComingSoon by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }

    // Liste des titres manga depuis Firestore
    var mangaTitles by remember { mutableStateOf(listOf<String>()) }

    // Charger les mangas depuis Firestore au lancement
    LaunchedEffect(Unit) {
        db.collection("mangas")
            .get()
            .addOnSuccessListener { result ->
                mangaTitles = result.documents.mapNotNull { it.getString("title") }
            }
            .addOnFailureListener {
                mangaTitles = listOf()
            }
    }

    if (showComingSoon) {
        ComingSoonScreen(onBack = { showComingSoon = false })
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
                        onClick = {
                            showAddDialog = true
                        },
                        modifier = Modifier
                            .height(56.dp)
                            .width(140.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Ajouter")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ajouter")
                    }
                }

                if (showAddDialog) {
                    AddMangaDialog(
                        onAdd = { newTitle ->
                            // Ajout dans Firestore
                            val newManga = hashMapOf("title" to newTitle)
                            db.collection("mangas")
                                .add(newManga)
                                .addOnSuccessListener {
                                    // Mettre à jour la liste locale
                                    mangaTitles = mangaTitles + newTitle
                                }
                            showAddDialog = false
                        },
                        onDismiss = { showAddDialog = false },
                        kitsuApi = ApiClient.kitsuService
                    )
                }
            }
        }
    }
}