package com.gajanan.newsapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gajanan.newsapp.domain.models.Article
import com.gajanan.newsapp.ui.screens.HomeScreen
import com.gajanan.newsapp.ui.screens.SavedScreen
import com.gajanan.newsapp.ui.theme.Background
import com.gajanan.newsapp.ui.theme.NewsAppTheme
import com.gajanan.newsapp.ui.views.BottomBar
import com.gajanan.newsapp.ui.screens.WebView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier
                        .background(
                            color = Background
                        )
                ) {
                    NewsApp()
                }
            }
        }
    }
}

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = navController,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController)
            }
            composable(route = Screen.WebViewScreen.route + "/{article}",
                arguments = listOf(
                    navArgument("article") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )
            ) { backStackEntry ->
                val jsonObject = backStackEntry.arguments?.getString("article") ?: ""
                val article = Gson().fromJson(jsonObject, Article::class.java)
                WebView(article = article)
            }
            composable(route = Screen.SavedScreen.route){
                SavedScreen(navController)
            }
        }
    }
}


