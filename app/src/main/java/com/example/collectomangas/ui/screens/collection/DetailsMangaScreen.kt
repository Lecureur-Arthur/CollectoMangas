package com.example.collectomangas.ui.screens.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.collectomangas.data.api.ApiClient
import com.example.collectomangas.data.model.MangaData
import kotlinx.coroutines.launch

@Composable
fun DetailsMangaScreen(title: String) {
    val scope = rememberCoroutineScope()
    var mangaData by remember { mutableStateOf<MangaData?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(title) {
        scope.launch {
            try {
                val response = ApiClient.kitsuService.searchManga(title)
                mangaData = response.data.firstOrNull()
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Erreur de chargement : ${e.message}"
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Text(
            text = errorMessage ?: "Erreur inconnue",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        mangaData?.let { data ->
            val englishTitle = data.attributes.titles?.get("en")
                ?: data.attributes.titles?.get("en_jp")
                ?: data.attributes.canonicalTitle

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = englishTitle,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = data.attributes.posterImage.original,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = data.attributes.synopsis ?: "Synopsis non disponible.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Statut : ${data.attributes.status ?: "Inconnu"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Nombre de volumes : ${data.attributes.volumeCount ?: "?"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Date de début : ${data.attributes.startDate ?: "?"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Date de fin : ${data.attributes.endDate ?: "?"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } ?: run {
            Text(
                text = "Aucun manga trouvé pour \"$title\"",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
