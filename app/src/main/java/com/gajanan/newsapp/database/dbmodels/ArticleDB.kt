package com.gajanan.newsapp.database.dbmodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gajanan.newsapp.domain.models.Article
import com.gajanan.newsapp.domain.models.Source

@Entity(tableName = "news")
data class ArticleDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val source: SourceDB?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    data class SourceDB(
        val id: String?,
        val name: String?
    )

    fun convertDBArticle():Article = Article(
        author = author,
        content = content,
        description = description,
        source = Source( source?.id, source?.name),
        title = title,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}
