package com.example.chall3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall3.model.Foods

class DetailViewModel : ViewModel() {

    private val _currentAmount = MutableLiveData(1)
    val currentAmount: LiveData<Int> = _currentAmount

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private val _selectedItem = MutableLiveData<Foods>()

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

}