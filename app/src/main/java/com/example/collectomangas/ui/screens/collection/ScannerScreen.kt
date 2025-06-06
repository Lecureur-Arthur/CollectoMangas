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

    // Demande la permission au lancement
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        var isbn by remember { mutableStateOf<String?>(null) }

        Column(modifier = Modifier.fillMaxSize()) {
            BarcodeScannerView { detectedISBN ->
                isbn = detectedISBN
            }

            isbn?.let {
                Text(
                    text = "ISBN détecté : $it",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    } else {
        // Affichage en cas de refus de permission
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Permission caméra requise pour scanner les ISBN")
        }
    }
}
