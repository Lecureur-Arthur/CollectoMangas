package com.example.collectomangas.ui.screens.collection

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.collectomangas.ui.screens.ComingSoonScreen
import com.example.collectomangas.ui.screens.DetailsMangaScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationCollection(modifier: Modifier =Modifier) {
    // Remember the NavController
    val navController = rememberNavController()

    // Set up the navigation graph
    NavHost(navController = navController, startDestination = RouteCollectionScreen) {
        composable<RouteCollectionScreen> {
            CollectionScreen(navController)
        }
        composable<RouteComingSoonScreen> {
            ComingSoonScreen(navController)
        }
        composable<RouteDetailsMangaScreen> {
            val title = it.toRoute < RouteDetailsMangaScreen > ().title
            DetailsMangaScreen(title)
        }
    }
}


@Serializable
object RouteCollectionScreen

@Serializable
data class RouteDetailsMangaScreen(val title: String)

@Serializable
object RouteComingSoonScreen
