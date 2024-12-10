package com.gajanan.newsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gajanan.newsapp.database.dbmodels.ArticleDB

@Database(entities = [ArticleDB::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao

}