package com.example.collectomangas.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.collectomangas.ui.navigation.BottomNavItem
import com.example.collectomangas.ui.theme.LightColorScheme

@Composable
fun Footer(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Collection,
        BottomNavItem.Read,
        BottomNavItem.Theory
    )

    NavigationBar(
        modifier = Modifier.shadow(8.dp, spotColor = Color.Black.copy(alpha = 0.25f)),
        containerColor = LightColorScheme.primary,
        contentColor = LightColorScheme.secondary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        //tint = if (selectedItem == index) Color.White else LightColorScheme.secondary
                    )
                },
                label = {
                    Text(
                        text = item.label.uppercase(),
                        //color = if (selectedItem == index) Color.White else LightColorScheme.secondary
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
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
