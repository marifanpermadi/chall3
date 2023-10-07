package com.example.chall3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.di.Injection
import com.example.chall3.repository.MenuPagingRepository

class MenuViewModel(
    private val repository: MenuPagingRepository
) : ViewModel() {

    fun getListMenu(): LiveData<PagingData<DataMenu>> =
        repository.getDataForPaging().cachedIn(viewModelScope)

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
                return MenuViewModel(Injection.provideRepository()) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}