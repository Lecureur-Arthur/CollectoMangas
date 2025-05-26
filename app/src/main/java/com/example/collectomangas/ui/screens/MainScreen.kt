package com.example.collectomangas.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.collectomangas.ui.components.Footer
import com.example.collectomangas.ui.components.Header
import com.example.collectomangas.ui.theme.LightColorScheme

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightColorScheme.background
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { Header() },
            bottomBar = {
                Footer(selectedItem = selectedItem, onItemSelected = { selectedItem = it })
            }
        ) { innerPadding ->
            when (selectedItem) {
                0 -> AccueilScreen(Modifier.padding(innerPadding))
                1 -> CollectionScreen(Modifier.padding(innerPadding))
                2 -> LectureScreen(Modifier.padding(innerPadding))
                3 -> TheorieScreen(Modifier.padding(innerPadding))
            }
        }
    }
}