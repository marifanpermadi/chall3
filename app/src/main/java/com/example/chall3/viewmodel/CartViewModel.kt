package com.example.chall3.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chall3.database.Cart
import com.example.chall3.repository.CartRepository

class CartViewModel(application: Application) : ViewModel() {

    private val cartRepository: CartRepository = CartRepository(application)

    val allCartItems: LiveData<List<Cart>> = cartRepository.getAllCartItems()

    fun deleteCartItemById(cartId: Long) {
        cartRepository.deleteById(cartId)
    }

}