package com.example.collectomangas.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.collectomangas.data.api.ApiClient
import com.example.collectomangas.data.model.MangaData
import kotlinx.coroutines.launch

@Composable
fun MangaCard(
    title: String,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var mangaData by remember { mutableStateOf<MangaData?>(null) }

    LaunchedEffect(title) {
        scope.launch {
            try {
                val response = ApiClient.kitsuService.searchManga(title)
                mangaData = response.data.firstOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    mangaData?.let { data ->
        val cardShape = RoundedCornerShape(16.dp)

        val englishTitle = data.attributes.titles?.get("en")
            ?: data.attributes.titles?.get("en_jp")
            ?: data.attributes.canonicalTitle

        Card(
            onClick = onClick,
            shape = cardShape,
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(0.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(data.attributes.posterImage.original),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 120.dp, height = 180.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                bottomStart = 16.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .fillMaxHeight(),  // <- Prend toute la hauteur dispo dans le Row
                    verticalArrangement = Arrangement.Center  // <- Centre verticalement les éléments dans la colonne
                ) {
                    Text(
                        text = englishTitle,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "volume :",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "${data.attributes.volumeCount ?: "Unknown"}",
                        style = MaterialTheme.typography.bodyMedium

                    )

                    Text(
                        text = "Last volume added :",
                        style = MaterialTheme.typography.titleMedium

                    )

                    Text(
                        text = "${data.attributes.volumeCount ?: "Unknown"}",
                        style = MaterialTheme.typography.bodyMedium

                    )
                }

            }
        }
    }?: run {
        Text(
            text = "Loading...",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}