package com.gajanan.newsapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gajanan.newsapp.domain.models.Article
import com.gajanan.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
@Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    val response = repository.getTopHeadlineResponse
    val getAllSavedArticles = repository.getAllSavedArticles()

    fun getTopHeadlines() = viewModelScope.launch(Dispatchers.IO) {
        repository.getTopHeadlineNews()
    }

    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveArticleInDB(article)
    }

    suspend fun isArticleSaved(article: Article): Boolean {
        return repository.isArticleSaved(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteArticle(article)
        repository.getAllSavedArticles()
    }
}