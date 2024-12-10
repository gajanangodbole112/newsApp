package com.gajanan.newsapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gajanan.newsapp.ui.activities.Screen
import com.gajanan.newsapp.ui.theme.Orange

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        Screen.HomeScreen,
        Screen.SavedScreen
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    if (currentRoute in items.map { it.route }) {
        NavigationBar(
            modifier = Modifier
                .height(80.dp),
            contentColor = Color.Gray,
            containerColor = Color.White,

        ) {
            items.forEach { screen ->
                val selected = currentRoute == screen.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon!!),
                            contentDescription = screen.title,
                            tint = if (selected) Orange else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            screen.title,
                            color = if (selected) Orange else Color.Gray
                        )
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                )
            }
        }
    }
}
