package com.example.collectomangas.ui.screens.collection

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
import androidx.navigation.NavController
import com.example.collectomangas.data.api.ApiClient
import com.example.collectomangas.ui.components.MangaCard
import com.example.collectomangas.ui.modal.AddMangaDialog
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CollectionScreen(navController: NavController) {
    var showAddDialog by remember { mutableStateOf(false) }

    var mangaTitles by remember { mutableStateOf(listOf<String>()) }

    val db = FirebaseFirestore.getInstance()


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
    Box(
        modifier = Modifier
//            .padding(
//                top = innerPadding.calculateTopPadding(),
//                bottom = innerPadding.calculateBottomPadding()
//            )
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
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    navController.navigate(RouteComingSoonScreen)
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
