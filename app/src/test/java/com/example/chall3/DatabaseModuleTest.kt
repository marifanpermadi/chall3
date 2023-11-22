package com.example.chall3

import android.content.Context
import com.example.chall3.di.DatabaseModule.provideCartDatabase
import com.example.chall3.di.DatabaseModule.provideCategoryDatabase
import com.example.chall3.di.DatabaseModule.provideMenuDatabase
import com.example.chall3.di.DatabaseModule.provideUserDatabase
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DatabaseModuleTest {

    @Mock
    private lateinit var context: Context


    @Test
    fun `test provideCartDatabase should create a non-null CartDatabase instance`() {
        val cartDatabase = provideCartDatabase(context)
        assertNotNull(cartDatabase)
    }

    @Test
    fun `test provideUserDatabase should create a non-null UserDatabase instance`() {
        val userDatabase = provideUserDatabase(context)
        assertNotNull(userDatabase)
    }

    @Test
    fun `test provideCategoryDatabase should create a non-null CategoryDatabase instance`() {
        val categoryDatabase = provideCategoryDatabase(context)
        assertNotNull(categoryDatabase)
    }

    @Test
    fun `test provideMenuDatabase should create a non-null MenuDatabase instance`() {
        val menuDatabase = provideMenuDatabase(context)
        assertNotNull(menuDatabase)
    }
}