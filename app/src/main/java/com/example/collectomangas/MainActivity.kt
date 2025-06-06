package com.example.collectomangas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.collectomangas.ui.screens.collection.DetailsMangaScreen
import com.example.collectomangas.ui.screens.MainScreen
import com.example.collectomangas.ui.screens.collection.ScannerScreen
import com.example.collectomangas.ui.theme.CollectoMangasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollectoMangasTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CollectoMangasTheme {
        //MainScreen()
        ScannerScreen()

    }
}

@Preview(showBackground = true)
@Composable
fun DetailsMangaScreenPreview() {
    DetailsMangaScreen("One piece")
}