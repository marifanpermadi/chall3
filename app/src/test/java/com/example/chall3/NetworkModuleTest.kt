package com.example.chall3

import com.example.chall3.di.NetworkModule.provideRetrofitInstance
import org.junit.Assert.assertNotNull
import okhttp3.OkHttpClient
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModuleTest {

    @Test
    fun `test provideRetrofitInstance should create a non-null Retrofit instance`() {

        val okHttpClient = OkHttpClient.Builder().build()
        val gsonConverterFactory = GsonConverterFactory.create()
        val retrofit = provideRetrofitInstance(okHttpClient, gsonConverterFactory)

        assertNotNull(retrofit)
    }
}