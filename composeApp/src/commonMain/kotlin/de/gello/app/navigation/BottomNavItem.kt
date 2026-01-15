package de.gello.app.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val route: Screen,
    val icon: ImageVector
)
