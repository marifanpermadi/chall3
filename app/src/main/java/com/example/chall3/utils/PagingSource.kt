package com.example.chall3.utils

import androidx.paging.PagingState
import androidx.paging.PagingSource
import com.example.chall3.data.api.ApiService
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.data.apimodel.ListMenuResponse

class PagingSource(
    private val apiService: ApiService,
    private val category: String ?= null
) : PagingSource<Int, DataMenu>() {

    override fun getRefreshKey(state: PagingState<Int, DataMenu>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataMenu> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData: ListMenuResponse = if (category != null) {
                apiService.getMenuByCategory(category)
            } else {
                apiService.getListMenu()
            }

            LoadResult.Page(
                data = responseData.data,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.data.size == 22) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
