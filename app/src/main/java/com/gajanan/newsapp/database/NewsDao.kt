package com.gajanan.newsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gajanan.newsapp.database.dbmodels.ArticleDB
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsArticle(item: ArticleDB)

    @Query("DELETE FROM news WHERE publishedAt = :publishedAt")
    suspend fun deleteArticle(publishedAt: String)

    @Query("SELECT EXISTS(SELECT 1 FROM news WHERE publishedAt = :publishedAt)")
    suspend fun isArticleSaved(publishedAt: String): Boolean

    @Query("SELECT * FROM news")
    fun getAllArticles(): Flow<List<ArticleDB>>
}