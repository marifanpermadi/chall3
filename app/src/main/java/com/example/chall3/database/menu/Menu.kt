package com.example.chall3.database.menu

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chall3.data.apimodel.ListMenuResponse

@Entity(tableName = "menu_table")
class Menu(
    var listMenuResponse: ListMenuResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}