package com.example.chall3.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.chall3.data.api.ApiService
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.utils.PagingSource

class MenuPagingRepository(
    private val apiService: ApiService
) {
    fun getDataForPaging(): LiveData<PagingData<DataMenu>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                maxSize = 22,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingSource(apiService)
            }
        ).liveData
    }

    fun getDataForPagingByCategory(category: String): LiveData<PagingData<DataMenu>> {

        val maximal: Int = when (category) {
            "Mie" -> {
                11
            }

            "Minuman" -> {
                3
            }

            "Burger" -> {
                3
            }

            else -> {
                7
            }
        }

        return Pager(
            config = PagingConfig(
                pageSize = 0,
                maxSize = maximal,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                PagingSource(apiService, category)
            }
        ).liveData
    }

}