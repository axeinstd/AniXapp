package tech.axeinstd.anixapp.screens.home.compose.navigation

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomNavBarItem (
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)