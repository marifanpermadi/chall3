package com.example.chall3.data.api

import com.example.chall3.data.apimodel.ListMenuResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("listmenu")
    suspend fun getListMenu(): ListMenuResponse

    @GET("listmenu")
    suspend fun getMenuByCategory(
        @Query("c") category: String
    ): ListMenuResponse
}