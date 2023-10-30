package com.example.chall3.database.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chall3.data.apimodel.CategoryResponse

@Entity(tableName = "category_table")
class Category(
    var categoryResponse: CategoryResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}