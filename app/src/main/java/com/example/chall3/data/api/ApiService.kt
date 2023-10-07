package com.example.chall3.data.api

import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.data.apimodel.ListMenuResponse
import com.example.chall3.data.apimodel.MenuByCategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("listmenu")
    suspend fun getListMenu(): ListMenuResponse

    @GET("listmenu?c=")
    suspend fun getMenuByCategory(
        @Query("c") category : String
    ): Call<MenuByCategoryResponse>
}