package com.example.chall3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.chall3.data.api.ApiConfig
import com.example.chall3.data.apimodel.CategoryResponse
import com.example.chall3.data.apimodel.DataCategory
import com.example.chall3.data.apimodel.DataMenu
import com.example.chall3.di.Injection
import com.example.chall3.repository.MenuPagingRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel(
    private val repository: MenuPagingRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _menuCategory = MutableLiveData<List<DataCategory>>()
    val menuCategory : LiveData<List<DataCategory>> = _menuCategory


    fun getListMenu(): LiveData<PagingData<DataMenu>> =
        repository.getDataForPaging().cachedIn(viewModelScope)

    fun getMenuByCategory(category: String): LiveData<PagingData<DataMenu>> =
        repository.getDataForPagingByCategory(category).cachedIn(viewModelScope)

    fun getMenuCategories() {
        val apiService = ApiConfig.getApiService()

        _isLoading.value = true
        apiService.getCategory().enqueue(object: Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _menuCategory.value = response.body()?.data
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })

    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
                return MenuViewModel(Injection.provideRepository()) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}