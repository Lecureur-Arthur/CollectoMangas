package com.example.collectomangas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectomangas.ui.theme.*

private val LightColorScheme = lightColorScheme(
    primary = RoseChaud,
    secondary = BeigeRose,
    background = RosePale
)

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
            modifier = Modifier.fillMaxSize(),
            topBar = { Header() },
            bottomBar = {
                footer(selectedItem = selectedItem, onItemSelected = { selectedItem = it })
            }
        ) { innerPadding ->
            when (selectedItem) {
                0 -> Body(Modifier.padding(innerPadding))
                1 -> CollectionScreen(Modifier.padding(innerPadding))
                2 -> LectureScreen(Modifier.padding(innerPadding))
                3 -> TheorieScreen(Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightColorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
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
            text = "COLLECTO'MANGAS",
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

@Composable
fun footer(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf("Accueil", "Collection", "Lecture", "Théorie")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.CollectionsBookmark,
        Icons.Default.MenuBook,
        Icons.Default.Book
    )

    NavigationBar(
        modifier = Modifier
            .shadow(8.dp, spotColor = Color.Black.copy(alpha = 0.25f)), // Ombre au-dessus
        containerColor = LightColorScheme.primary,
        contentColor = LightColorScheme.secondary
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item,
                        tint = if (selectedItem == index) Color.White else LightColorScheme.secondary
                    )
                },
                label = {
                    Text(
                        text = item.uppercase(),
                        color = if (selectedItem == index) Color.White else LightColorScheme.secondary
                    )
                },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = LightColorScheme.secondary.copy(alpha = 0.2f),
                    unselectedIconColor = LightColorScheme.secondary,
                    unselectedTextColor = LightColorScheme.secondary
                )
            )
        }
    }
}

@Composable
fun Body(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Collecto'Mangas est l’application idéale pour tous les passionnés de mangas qui veulent garder leur collection bien organisée et leur imagination toujours active !\n\n" +
                    "Voici ce que tu peux faire avec Collecto'Mangas :\n\n" +
                    "📖 Suivi de collection physique\nIndique facilement les tomes que tu possèdes déjà, pour ne plus jamais acheter un doublon par erreur !\n\n" +
                    "🔖 Progression de lecture\nGarde une trace du dernier chapitre que tu as lu pour chaque manga, et reprends ta lecture exactement là où tu t’étais arrêté.\n\n" +
                    "🧠 Espace théories\nUne idée de twist ? Une théorie sur le prochain arc ? Note toutes tes hypothèses et réflexions directement dans l’appli, manga par manga !\n\n" +
                    "Collecto'Mangas, c’est ton espace personnel pour vivre encore plus intensément ta passion du manga. Organisé, simple et conçu pour les vrais fans.",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        )
    }
}

@Composable
fun CollectionScreen(modifier: Modifier = Modifier) {
    Text(text = "Voici ta collection.", modifier = modifier.padding(16.dp))
}

@Composable
fun LectureScreen(modifier: Modifier = Modifier) {
    Text(text = "Zone de lecture.", modifier = modifier.padding(16.dp))
}

@Composable
fun TheorieScreen(modifier: Modifier = Modifier) {
    Text(text = "Théorie et informations.", modifier = modifier.padding(16.dp))
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CollectoMangasTheme {
        MainScreen()
    }
}
