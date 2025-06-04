package com.example.collectomangas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.collectomangas.ui.screens.collection.NavigationCollection
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCollectionScreen(modifier: Modifier = Modifier) {

    Scaffold() { innerPadding ->
        NavigationCollection(modifier = Modifier.padding(innerPadding))
    }
}