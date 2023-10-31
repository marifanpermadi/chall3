package com.example.chall3.data.api

import com.example.chall3.data.apimodel.CategoryResponse
import com.example.chall3.data.apimodel.ListMenuResponse
import com.example.chall3.data.apimodel.OrderRequest
import com.example.chall3.data.apimodel.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("listmenu")
    suspend fun getListMenuDI(): Response<ListMenuResponse>

    @GET("category")
    suspend fun getCategoryDI(): Response<CategoryResponse>

    @GET("listmenu")
    suspend fun getMenuByCategoryDI(
        @Query("c") category: String
    ): Response<ListMenuResponse>

    @POST("order")
    suspend fun placeOrderDI(
        @Body orderRequest: OrderRequest
    ) : Response<OrderResponse>

}