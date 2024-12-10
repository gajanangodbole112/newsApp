package com.gajanan.newsapp.database

import androidx.room.TypeConverter
import com.gajanan.newsapp.database.dbmodels.ArticleDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun fromSourceDB(source: ArticleDB.SourceDB?): String? {
        return gson.toJson(source)
    }

    @TypeConverter
    fun toSourceDB(sourceJson: String?): ArticleDB.SourceDB? {
        return sourceJson?.let {
            gson.fromJson(it, object : TypeToken<ArticleDB.SourceDB>() {}.type)
        }
    }
}