package com.example.collectomangas.ui.screens.collection

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.collectomangas.data.camera.BarcodeScannerView
import com.example.collectomangas.data.api.GoogleBooksApi
import com.example.collectomangas.data.FirebaseMangaRepository
import kotlinx.coroutines.launch

@Composable
fun ScannerScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    var isbn by remember { mutableStateOf<String?>(null) }
    var title by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    if (hasCameraPermission) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BarcodeScannerView { detectedISBN ->
                isbn = detectedISBN
                coroutineScope.launch {
                    title = GoogleBooksApi.getBookTitleByISBN(detectedISBN)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            isbn?.let {
                Text("ISBN détecté : $it")
            }

            title?.let {
                Text("Titre du livre : $it", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    FirebaseMangaRepository.addManga(
                        title = it,
                        onSuccess = {
                            successMessage = "Manga ajouté avec succès dans Firebase !"
                            errorMessage = null
                        },
                        onFailure = { e ->
                            errorMessage = "Erreur : ${e.message}"
                            successMessage = null
                        }
                    )
                }) {
                    Text("Ajouter à ma collection")
                }
            }

            successMessage?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = MaterialTheme.colorScheme.primary)
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Permission caméra requise pour scanner.")
        }
    }
}
