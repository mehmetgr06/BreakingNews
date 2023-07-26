package com.example.breakingnews.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.breakingnews.ui.model.Article

@Database(
    entities = [Article::class],
    version = 2,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDao: NewsDao

    companion object {
        const val DATABASE_NAME = "news_db"
    }
}
