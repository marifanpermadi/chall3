package com.example.chall3.database.category

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chall3.database.CategoryTypeConverter

@Database(
    entities = [Category::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(CategoryTypeConverter::class)
abstract class CategoryDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
}