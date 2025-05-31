package com.example.collectomangas.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.collectomangas.ui.theme.LightColorScheme

@Composable
fun Footer(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf("Home", "Collection", "Reading", "Theory")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.CollectionsBookmark,
        Icons.AutoMirrored.Filled.MenuBook,
        Icons.Default.Book
    )

    NavigationBar(
        modifier = Modifier.shadow(8.dp, spotColor = Color.Black.copy(alpha = 0.25f)),
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
