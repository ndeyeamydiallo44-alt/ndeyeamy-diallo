package com.example.ui.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard

sealed class NavScreen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : NavScreen("dashboard", "Tableau", Icons.Default.Dashboard)
    object Finances : NavScreen("finances", "Finances", Icons.Default.AccountBalance)
    object Mastery : NavScreen("mastery", "Maîtrise", Icons.Default.TrackChanges)
    object Simulator : NavScreen("simulator", "FIRE Pro", Icons.Default.AutoGraph)
    object Principles : NavScreen("principles", "Principes", Icons.Default.MenuBook)
}

@Composable
fun AscendBottomNavigation(
    currentScreen: NavScreen,
    onNavigate: (NavScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavScreen.Dashboard,
        NavScreen.Finances,
        NavScreen.Mastery,
        NavScreen.Simulator,
        NavScreen.Principles
    )

    NavigationBar(
        modifier = modifier.navigationBarsPadding(),
        containerColor = MidnightNavy,
        tonalElevation = 8.dp
    ) {
        items.forEach { screen ->
            val isSelected = currentScreen == screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ObsidianDark,
                    selectedTextColor = EmeraldLight,
                    indicatorColor = EmeraldLight,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_item_${screen.route}")
            )
        }
    }
}
