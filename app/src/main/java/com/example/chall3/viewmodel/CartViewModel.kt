package com.example.chall3.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.chall3.data.Repository
import com.example.chall3.data.apimodel.OrderRequest
import com.example.chall3.data.apimodel.OrderResponse
import com.example.chall3.database.cart.Cart
import com.example.chall3.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val allCartItems: LiveData<List<Cart>> = repository.local.getAllCartItems()

    private val _cartItemLiveData = MutableLiveData<Cart>()

    var totalPrice: LiveData<Int> = calculateTotalPrice()

    private val _orderPlacedLiveData = MutableLiveData<NetworkResult<Boolean>>()
    val orderPlacedLiveData: LiveData<NetworkResult<Boolean>> = _orderPlacedLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun deleteCartItemById(cartId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.local.deleteById(cartId)
        }
    }

    private fun deleteAllItems() {
        viewModelScope.launch (Dispatchers.IO) {
            repository.local.deleteAllItems()
        }
    }

    fun updateCart(cart: Cart) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.local.updateCart(cart)
            _cartItemLiveData.postValue(cart)
        }

    }

    fun placeOrder(orderRequest: OrderRequest) = viewModelScope.launch {
        placeOrderSafeCall(orderRequest)
    }
    private suspend fun placeOrderSafeCall(orderRequest: OrderRequest) {

        _isLoading.value = true
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.placeOrder(orderRequest)
                _orderPlacedLiveData.value = handleOrderResponse(response)
                _isLoading.value = false
            } catch (e: Exception) {
                _orderPlacedLiveData.value = NetworkResult.Error("Error $e")
                _isLoading.value = false
            }
        } else {
            _orderPlacedLiveData.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleOrderResponse(response: Response<OrderResponse>): NetworkResult<Boolean> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }

            response.isSuccessful -> {
                deleteAllItems()
                return NetworkResult.Success(true)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun calculateTotalPrice() : LiveData<Int> {
        return repository.local.getAllCartItems().map { cartItems ->
            var total = 0
            for (cartItem in cartItems) {
                total += cartItem.basePrice * cartItem.orderAmount
            }
            total
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}