package com.example.chall3.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall3.database.Cart
import com.example.chall3.repository.CartRepository

class CartViewModel(application: Application) : ViewModel() {

    private val cartRepository: CartRepository = CartRepository(application)

    val allCartItems: LiveData<List<Cart>> = cartRepository.getAllCartItems()

    private val _cartItemLiveData = MutableLiveData<Cart>()

    var totalPrice: LiveData<Int> = cartRepository.calculateTotalPrice()

    fun deleteCartItemById(cartId: Long) {
        cartRepository.deleteById(cartId)
    }

    fun updateCart(cart: Cart) {
        cartRepository.update(cart)
        _cartItemLiveData.value = cart
    }

}