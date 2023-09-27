package com.example.chall3.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.chall3.database.Cart
import com.example.chall3.database.CartDao
import com.example.chall3.database.CartDataBase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartRepository(application: Application) {

    private val mCartDao: CartDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = CartDataBase.getDataBase(application)
        mCartDao = db.cartDao()
    }

    fun insert(cart: Cart) {
        executorService.execute {mCartDao.insert(cart)}
    }

    fun delete(cart: Cart) {
        executorService.execute {mCartDao.delete(cart)}
    }

    fun getAllCartItems(): LiveData<List<Cart>> = mCartDao.getAllCartItems()

}