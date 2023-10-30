package com.example.chall3.database.menu

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chall3.database.MenuTypeConverter

@Database(
    entities = [Menu::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(MenuTypeConverter::class)
abstract class MenuDatabase: RoomDatabase() {

    abstract fun menuDao(): MenuDao
}