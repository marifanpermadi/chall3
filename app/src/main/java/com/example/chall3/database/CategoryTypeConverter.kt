package com.example.chall3.database

import androidx.room.TypeConverter
import com.example.chall3.data.apimodel.CategoryResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun categoryToString(categoryResponse: CategoryResponse): String {
        return gson.toJson(categoryResponse)
    }

    @TypeConverter
    fun stringToCategory(data: String) : CategoryResponse {
        val listType = object : TypeToken<CategoryResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}