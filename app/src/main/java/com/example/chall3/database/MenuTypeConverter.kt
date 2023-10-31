package com.example.chall3.database

import androidx.room.TypeConverter
import com.example.chall3.data.apimodel.ListMenuResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MenuTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun menuToString(listMenuResponse: ListMenuResponse): String {
        return gson.toJson(listMenuResponse)
    }

    @TypeConverter
    fun stringToMenu(data: String) : ListMenuResponse {
        val listType = object : TypeToken<ListMenuResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}