package com.gajanan.newsapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController
import com.gajanan.newsapp.database.dbmodels.ArticleDB
import com.gajanan.newsapp.ui.activities.Screen
import com.gajanan.newsapp.ui.viewModels.NewsViewModel
import com.gajanan.newsapp.ui.views.NewsItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SavedScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<NewsViewModel>()
    var savedArticles by remember { mutableStateOf(emptyList<ArticleDB>()) }
    LaunchedEffect(viewModel) {
        viewModel.getAllSavedArticles.collectLatest {
            savedArticles = it
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (savedArticles.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(savedArticles.size) { index ->
                    val article = savedArticles[index].convertDBArticle()
                    NewsItem(
                        article = article,
                        showDeleteButton = true,
                        deleteButtonClick = {
                            viewModel.deleteArticle(article)
                        }
                    ) {
                        navController.navigate(
                            Screen.WebViewScreen.route +
                                    "/${Uri.encode(Gson().toJson(article))}"
                        )
                    }
                }
            }
        }else{
            Text(
                text = "No Articles Available!",
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
