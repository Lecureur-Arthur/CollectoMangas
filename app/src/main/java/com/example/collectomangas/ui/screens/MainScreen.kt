package com.example.collectomangas.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collectomangas.ui.components.Footer
import com.example.collectomangas.ui.components.Header
import com.example.collectomangas.ui.navigation.BottomNavItem
import com.example.collectomangas.ui.theme.LightColorScheme


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightColorScheme.background
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { Header() },
            bottomBar = {
                Footer(navController)
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Home.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(BottomNavItem.Home.route) { AccueilScreen(Modifier.padding(padding)) }
                composable(BottomNavItem.Collection.route) { MainCollectionScreen(Modifier.padding(padding)) }
                composable(BottomNavItem.Read.route) { LectureScreen(Modifier.padding(padding)) }
                composable(BottomNavItem.Theory.route) { TheorieScreen(Modifier.padding(padding)) }
            }
        }
    }
}