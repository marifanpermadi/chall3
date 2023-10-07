package com.example.chall3.di

import com.example.chall3.data.api.ApiConfig
import com.example.chall3.repository.MenuPagingRepository

object Injection {

    fun provideRepository(): MenuPagingRepository {
        val apiService = ApiConfig.getApiService()
        return MenuPagingRepository(apiService)
    }
}