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
                pageSize = 1
            ),
            pagingSourceFactory = {
                PagingSource(apiService)
            }
        ).liveData
    }
}