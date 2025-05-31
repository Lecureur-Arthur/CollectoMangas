package com.example.collectomangas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.collectomangas.ui.theme.LightColorScheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip


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


@Composable
fun AddMangaDialog(
    onAdd: (String) -> Unit,
    onDismiss: () -> Unit,
    kitsuApi: com.example.collectomangas.data.api.KitsuApiService
) {
    var searchQuery by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    var searchJob by remember { mutableStateOf<Job?>(null) }

    fun searchManga(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            suggestions = emptyList()
            errorMessage = null
            return
        }
        searchJob = scope.launch {
            delay(500) // debounce 500ms
            isLoading = true
            errorMessage = null
            try {
                val response = kitsuApi.searchManga(query)
                suggestions = response.data.mapNotNull { it.attributes?.canonicalTitle }
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Erreur réseau : ${e.localizedMessage}"
                isLoading = false
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajouter un manga") },
        text = {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        searchManga(it)
                    },
                    label = { Text("Rechercher un manga") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (isLoading) {
                    Text("Recherche en cours...", style = MaterialTheme.typography.bodySmall)
                }

                if (errorMessage != null) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                }

                if (suggestions.isNotEmpty()) {
                    Text(
                        text = "Touchez un titre ci-dessous pour l’ajouter à votre collection :",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                ) {
                    items(suggestions) { title ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(LightColorScheme.primary)
                                .clickable {
                                    onAdd(title)
                                    onDismiss()
                                }
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Ajouter",
                                tint = MaterialTheme.colorScheme.onSecondary // texte contrasté
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            }
        },
        confirmButton = { /* rien ici */ },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )

}
