package com.gajanan.newsapp.ui.screens

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.gajanan.newsapp.domain.models.Article
import com.gajanan.newsapp.ui.theme.Orange
import com.gajanan.newsapp.ui.viewModels.NewsViewModel

@Composable
fun WebView(
    article: Article
) {
    val viewModel = hiltViewModel<NewsViewModel>()
    var isPageLoaded by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(article) {
        isSaved = viewModel.isArticleSaved(article)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            Log.d("WEBVIEW", "onPageStarted: $isPageLoaded")
                            if (!isPageLoaded) {
                                isPageLoaded = false
                            }
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            Log.d("WEBVIEW", "onPageFinished: $isPageLoaded")
                          //  if (!hasError) {
                                isPageLoaded = true
                        //    }
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            Log.d("WEBVIEW", "onReceivedError: $isPageLoaded")
                            hasError = true
                            // isPageLoaded = false
                        }

                        override fun onReceivedHttpError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            errorResponse: WebResourceResponse?
                        ) {
                            super.onReceivedHttpError(view, request, errorResponse)
                            Log.d("WEBVIEW", "onReceivedHttpError: $isPageLoaded")
                            hasError = true
                            // isPageLoaded = false
                        }
                    }
                    //  settings.javaScriptEnabled = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            update = {
                it.loadUrl(article.url ?: "")
            }
        )

        if (isPageLoaded) {
            Button(
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = Orange,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(5.dp),
                enabled = !isSaved,
                onClick = {
                    viewModel.saveArticle(article)
                    isSaved = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text(text = if (isSaved) "Saved" else "Save")
            }
        }
    }
}
