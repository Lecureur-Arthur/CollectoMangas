package fr.ensim.android.composelist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.collectomangas.ui.screens.AccueilScreen
import com.example.collectomangas.ui.screens.CollectionScreen
import com.example.collectomangas.ui.screens.ComingSoonScreen
import com.example.collectomangas.ui.screens.DetailsMangaScreen
import com.example.collectomangas.ui.screens.LectureScreen
import com.example.collectomangas.ui.screens.TheorieScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation(modifier: Modifier =Modifier) {
    // Remember the NavController
    val navController = rememberNavController()

    // Set up the navigation graph
    NavHost(navController = navController, startDestination = RouteAccueilScreen) {
        composable<RouteAccueilScreen> {
            AccueilScreen(modifier)
        }
        composable<RouteCollectionScreen> {
            val id = it.toRoute < RouteCollectionScreen > ().id
            CollectionScreen(modifier)
        }
        composable<RouteTheorieScreen> {
            TheorieScreen(modifier)
        }
        composable<RouteLectureScreen> {
            LectureScreen(modifier)
        }
        composable<RouteComingSoonScreen> {
            ComingSoonScreen(onBack = {})
        }
        composable<RouteDetailsMangaScreen> {
            val title = it.toRoute < RouteDetailsMangaScreen > ().title
            DetailsMangaScreen(title)
        }
    }
}


@Serializable
data class RouteCollectionScreen(val id: Int)

@Serializable
object RouteTheorieScreen

@Serializable
object RouteLectureScreen

@Serializable
object RouteComingSoonScreen

@Serializable
object RouteAccueilScreen

@Serializable
data class RouteDetailsMangaScreen(val title: String)
