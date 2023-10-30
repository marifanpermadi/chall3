package com.example.chall3.database.menu

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: Menu)

    @Query("SELECT * FROM menu_table ORDER BY id ASC")
    fun readMenu(): Flow<List<Menu>>
}