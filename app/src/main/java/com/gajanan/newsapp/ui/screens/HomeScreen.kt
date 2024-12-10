package com.gajanan.newsapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gajanan.newsapp.domain.models.Article
import com.gajanan.newsapp.ui.activities.Screen
import com.gajanan.newsapp.ui.theme.Orange
import com.gajanan.newsapp.ui.viewModels.NewsViewModel
import com.gajanan.newsapp.ui.views.NewsItem
import com.gajanan.newsapp.utils.ResultApi
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val viewModel = hiltViewModel<NewsViewModel>()
        var topHeadlineList by remember { mutableStateOf(emptyList<Article>()) }
        var showLoadingIndicator by remember { mutableStateOf(false) }
        var showError by remember { mutableStateOf(false) }
        var showErrorMessage by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            viewModel.getTopHeadlines()
        }
        LaunchedEffect(key1 = viewModel) {
            viewModel.response.collectLatest {
                when (it) {
                    is ResultApi.Error -> {
                        showLoadingIndicator = false
                        showError = true
                        showErrorMessage = it.error?.message ?: ""
                    }

                    is ResultApi.Loading -> {
                        showLoadingIndicator = true
                        showError = false
                    }

                    is ResultApi.Success -> {
                        showLoadingIndicator = false
                        showError = false
                        topHeadlineList = it.data?.articles ?: emptyList()
                    }
                }
            }
        }
        if (showError && showErrorMessage.isNotEmpty()) {
            Text(
                text = showErrorMessage,
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (showLoadingIndicator) {
            CircularProgressIndicator(
                color = Orange
            )
        }

        if (topHeadlineList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(topHeadlineList.size) { index ->
                    NewsItem(
                        article = topHeadlineList[index],
                        deleteButtonClick = { }
                    ) {
                        navController.navigate(
                            Screen.WebViewScreen.route +
                                    "/${Uri.encode(Gson().toJson(topHeadlineList[index]))}"
                        )
                    }
                }
            }
        }
    }
}