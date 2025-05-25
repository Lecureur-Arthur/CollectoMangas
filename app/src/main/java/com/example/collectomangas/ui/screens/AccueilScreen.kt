package com.example.collectomangas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccueilScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(scrollState),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Collecto'Mangas est l’application idéale pour tous les passionnés de mangas qui veulent garder leur collection bien organisée et leur imagination toujours active !\n",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            )

            Text(
                text = "📖 Suivi de collection physique",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = "Indique facilement les tomes que tu possèdes déjà, pour ne plus jamais acheter un doublon par erreur !\n",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            )

            Text(
                text = "🔖 Progression de lecture",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = "Garde une trace du dernier chapitre que tu as lu pour chaque manga, et reprends ta lecture exactement là où tu t’étais arrêté.\n",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            )

            Text(
                text = "🧠 Espace théories",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = "Une idée de twist ? Une théorie sur le prochain arc ? Note toutes tes hypothèses et réflexions directement dans l’appli, manga par manga !\n",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            )

            Text(
                text = "Collecto'Mangas, c’est ton espace personnel pour vivre encore plus intensément ta passion du manga. Organisé, simple et conçu pour les vrais fans.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


