package com.example.collectomangas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.collectomangas.R
import com.example.collectomangas.ui.theme.LightColorScheme

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightColorScheme.primary)
            .padding(WindowInsets.statusBars.asPaddingValues()) // <- Ajout du padding système
            .padding(horizontal = 16.dp, vertical = 12.dp), // padding habituel
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.MenuBook,
            contentDescription = "Logo Collecto'Mangas",
            modifier = Modifier.size(32.dp),
            tint = LightColorScheme.secondary
        )

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = LightColorScheme.secondary
        )

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profil utilisateur",
            modifier = Modifier.size(32.dp),
            tint = LightColorScheme.secondary
        )
    }
}

