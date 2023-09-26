package com.example.chall3.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall3.model.Foods

class HomeViewModel: ViewModel() {
    val isListView = MutableLiveData<Boolean>().apply { value = true }
    val foodItems = MutableLiveData<ArrayList<Foods>>()
}