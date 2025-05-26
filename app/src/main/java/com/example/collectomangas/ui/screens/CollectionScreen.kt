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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.collectomangas.ui.components.MangaCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(modifier: Modifier = Modifier) {
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
            // Contenu défilable en arrière-plan (par exemple tes cartes)
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 100.dp) // espace pour boutons si besoin
            ) {
                MangaCard(title = "One Piece")
                MangaCard(title = "My Dress-Up Darling")
                MangaCard(title = "The Quintessential Quintuplets")
                MangaCard(title = "Shikimori's Not Just a Cutie")
                MangaCard(title = "Jujutsu Kaisen")
                MangaCard(title = "Horimiya")
                MangaCard(title = "Demon Slayer: Kimetsu no Yaiba")
                MangaCard(title = "Darling in the franxx")
            }

            // Row centré au milieu de l'écran
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
                    onClick = { /* Action scanner */ },
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
