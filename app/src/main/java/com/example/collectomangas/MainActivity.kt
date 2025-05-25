package com.example.collectomangas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.collectomangas.ui.components.Footer
import com.example.collectomangas.ui.components.Header
import com.example.collectomangas.ui.screens.AccueilScreen
import com.example.collectomangas.ui.screens.CollectionScreen
import com.example.collectomangas.ui.screens.LectureScreen
import com.example.collectomangas.ui.screens.TheorieScreen
import com.example.collectomangas.ui.theme.CollectoMangasTheme
import com.example.collectomangas.ui.theme.LightColorScheme

//private val LightColorScheme = lightColorScheme(
//    primary = RoseChaud,
//    secondary = BeigeRose,
//    background = RosePale
//)

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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CollectoMangasTheme {
        MainScreen()
    }
}
