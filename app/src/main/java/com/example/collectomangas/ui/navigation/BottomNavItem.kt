package com.example.collectomangas.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Collection : BottomNavItem("collection", "Collection", Icons.Default.CollectionsBookmark,)
    object Read : BottomNavItem("reading", "Reading", Icons.AutoMirrored.Filled.MenuBook,)
    object Theory : BottomNavItem("theory", "Theory", Icons.Default.Book)
}