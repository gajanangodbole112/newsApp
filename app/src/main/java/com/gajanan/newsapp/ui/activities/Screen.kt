package com.gajanan.newsapp.ui.activities

import com.gajanan.newsapp.R

sealed class Screen(val route: String, val title: String, val icon:Int?=null) {
    data object HomeScreen : Screen("destination_home", "Home", R.drawable.home_icon)
    data object WebViewScreen : Screen("destination_webview", "WebView")
    data object SavedScreen : Screen("destination_saved", "Saved",R.drawable.saved_icon)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}