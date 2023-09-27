package com.example.chall3.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall3.database.Cart
import com.example.chall3.model.Foods
import com.example.chall3.repository.CartRepository

class DetailViewModel(application: Application) : ViewModel() {

    private val _currentAmount = MutableLiveData(1)
    val currentAmount: LiveData<Int> = _currentAmount

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private val _orderNote = MutableLiveData<String?>()
    val orderNote: LiveData<String?> = _orderNote

    private val _selectedItem = MutableLiveData<Foods>()

    private val cartRepository: CartRepository

    init {
        cartRepository = CartRepository(application)
    }

    fun initSelectedItem(item: Foods) {
        _selectedItem.value = item
        _totalPrice.value = item.price
    }

    fun setCurrentAmount(amount: Int) {
        _currentAmount.value = amount
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val currentAmount = _currentAmount.value ?: 1
        val selectedItem = _selectedItem.value
        if (selectedItem != null) {
            val totalPrice = selectedItem.price * currentAmount
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

    private fun insertCartItem(cartItem: Cart) {
        cartRepository.insert(cartItem)
    }

    fun addToCart() {
        val selectedItem = _selectedItem.value

        selectedItem?.let {
            val cartItem = totalPrice.value?.let { it1 ->
                Cart(
                    foodImage = it.photo,
                    foodName = it.name,
                    foodPrice = it1,
                    orderNote = getOrderNote()
                )
            }
            cartItem?.let { it1 -> insertCartItem(it1) }
        }
    }

}