package com.example.chall3.data

import com.example.chall3.data.api.ApiService
import com.example.chall3.data.apimodel.CategoryResponse
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.data.apimodel.ListMenuResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getListMenu() : Response<ListMenuResponse> {
        return apiService.getListMenuDI()
    }

    suspend fun getCategoryMenu() : Response<CategoryResponse> {
        return apiService.getCategoryDI()
    }

}