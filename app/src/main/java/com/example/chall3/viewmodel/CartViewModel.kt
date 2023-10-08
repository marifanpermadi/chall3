package com.example.chall3.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall3.data.api.ApiConfig
import com.example.chall3.data.apimodel.OrderRequest
import com.example.chall3.data.apimodel.OrderResponse
import com.example.chall3.database.Cart
import com.example.chall3.repository.CartRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(application: Application) : ViewModel() {

    private val cartRepository: CartRepository = CartRepository(application)

    val allCartItems: LiveData<List<Cart>> = cartRepository.getAllCartItems()

    private val _cartItemLiveData = MutableLiveData<Cart>()

    var totalPrice: LiveData<Int> = cartRepository.calculateTotalPrice()

    private val _orderPlacedLiveData = MutableLiveData<Boolean>()
    val orderPlacedLiveData: LiveData<Boolean>
        get() = _orderPlacedLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun deleteCartItemById(cartId: Long) {
        cartRepository.deleteById(cartId)
    }

    fun deleteAllItems() {
        cartRepository.deleteAllItems()
    }

    fun updateCart(cart: Cart) {
        cartRepository.update(cart)
        _cartItemLiveData.value = cart
    }

    fun placeOrder(orderRequest: OrderRequest) {
        val apiService = ApiConfig.getApiService()

        _isLoading.value = true
        apiService.placeOrder(orderRequest).enqueue(object: Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    deleteAllItems()
                    _orderPlacedLiveData.postValue(true)
                    _isLoading.value = false
                } else {
                    Log.d("OrderFailed", response.message())
                    _orderPlacedLiveData.postValue(false)
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Log.d("OrderFailed", t.message.toString())
                _orderPlacedLiveData.postValue(false)
                _isLoading.value = false
            }

        })
    }

}