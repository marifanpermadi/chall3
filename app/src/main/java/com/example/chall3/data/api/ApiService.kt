package com.example.chall3.data.api

import com.example.chall3.data.apimodel.ListMenuResponse
import com.example.chall3.data.apimodel.OrderRequest
import com.example.chall3.data.apimodel.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("listmenu")
    suspend fun getListMenu(): ListMenuResponse

    @GET("listmenu")
    suspend fun getMenuByCategory(
        @Query("c") category: String
    ): ListMenuResponse

    @POST("order")
    fun placeOrder(
        @Body orderRequest: OrderRequest
    ): Call<OrderResponse>

}