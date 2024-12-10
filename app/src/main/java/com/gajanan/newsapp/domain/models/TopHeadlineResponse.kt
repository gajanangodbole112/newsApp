package com.gajanan.newsapp.domain.models

import com.gajanan.newsapp.database.dbmodels.ArticleDB

data class TopHeadlineResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    fun mapToArticleDB(): ArticleDB = ArticleDB(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt ?: "0",
        source = source?.mapToSourceDB(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

data class Source(
    val id: String?,
    val name: String?
) {
    fun mapToSourceDB(): ArticleDB.SourceDB = ArticleDB.SourceDB(
        id = id,
        name = name
    )
}