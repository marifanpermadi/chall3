package com.example.chall3.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
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
        executorService.execute { mCartDao.insert(cart) }
    }

    fun deleteById(cartId: Long) {
        executorService.execute { mCartDao.deleteById(cartId) }
    }

    fun update(cart: Cart) {
        executorService.execute { mCartDao.update(cart) }
    }

    fun deleteAllItems() {
        executorService.execute { mCartDao.deleteALlItems() }
    }

    fun getAllCartItems(): LiveData<List<Cart>> = mCartDao.getAllCartItems()

    fun calculateTotalPrice() : LiveData<Int> {
        return mCartDao.getAllCartItems().map { cartItems ->
            var total = 0
            for (cartItem in cartItems) {
                total += cartItem.basePrice * cartItem.orderAmount
            }
            total
        }
    }

}