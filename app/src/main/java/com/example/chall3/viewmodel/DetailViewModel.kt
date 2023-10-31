package com.example.chall3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chall3.data.Repository
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.database.cart.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _currentAmount = MutableLiveData(1)
    val currentAmount: LiveData<Int> = _currentAmount

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private val _orderNote = MutableLiveData<String?>()
    val orderNote: LiveData<String?> = _orderNote

    private val _selectedItem = MutableLiveData<DataMenu>()


    fun initSelectedItem(item: DataMenu) {
        _selectedItem.value = item
        _totalPrice.value = item.harga
    }

    fun setCurrentAmount(amount: Int) {
        _currentAmount.value = amount
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val currentAmount = _currentAmount.value ?: 1
        val selectedItem = _selectedItem.value
        if (selectedItem != null) {
            val totalPrice = selectedItem.harga * currentAmount
            _totalPrice.value = totalPrice
        }
    }

    fun clearTotalPrice(item: Int) {
        _totalPrice.value = item
    }

    fun setOrderNote(note: String?) {
        _orderNote.value = note
    }

    private fun getOrderNote(): String? {
        return orderNote.value
    }

    private suspend fun insertCartItem(cartItem: Cart) {
        repository.local.insertCart(cartItem)
    }

    fun addToCart() = viewModelScope.launch {
        val selectedItem = _selectedItem.value

        selectedItem?.let {
            val cartItem =
                totalPrice.value?.let { it1 ->
                    currentAmount.value?.let { it2 ->
                        Cart(
                            foodImage = it.imageUrl,
                            foodName = it.nama,
                            foodPrice = it1,
                            orderNote = getOrderNote(),
                            orderAmount = it2,
                            basePrice = it.harga
                        )
                    }
                }
            cartItem?.let { it1 -> insertCartItem(it1) }
        }
    }

}