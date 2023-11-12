package com.example.chall3.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.chall3.data.Repository
import com.example.chall3.data.apimodel.CategoryResponse
import com.example.chall3.data.apimodel.ListMenuResponse
import com.example.chall3.database.category.Category
import com.example.chall3.database.menu.Menu
import com.example.chall3.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MenuViewModelDI @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM DATABASE**/
    val readMenu: LiveData<List<Menu>> = repository.local.readMenu().asLiveData()
    val readCategory: LiveData<List<Category>> = repository.local.readCategory().asLiveData()

    private fun insertMenu(menu: Menu) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertMenu(menu)
        }

    private fun insertCategory(category: Category) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertCategory(category)
        }


    /** RETROFIT **/
    var listMenuResponse: MutableLiveData<NetworkResult<ListMenuResponse>> = MutableLiveData()
    var listMenuByCategoryResponse: MutableLiveData<NetworkResult<ListMenuResponse>> =
        MutableLiveData()
    var categoryResponse: MutableLiveData<NetworkResult<CategoryResponse>> = MutableLiveData()


    //==============================================MENU==============================================//
    fun getListMenu() = viewModelScope.launch {
        getListMenuSafeCall()
    }

    private suspend fun getListMenuSafeCall() {
        listMenuResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getListMenu()
                listMenuResponse.value = handleListMenuResponse(response)

                val listMenu = listMenuResponse.value!!.data
                if (listMenu != null) {
                    offlineCacheMenu(listMenu)
                }
            } catch (e: Exception) {
                listMenuResponse.value = NetworkResult.Error("Error: $e")
            }
        } else {
            listMenuResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheMenu(listMenu: ListMenuResponse) {
        val menu = Menu(listMenu)
        insertMenu(menu)
    }

    private fun handleListMenuResponse(response: Response<ListMenuResponse>): NetworkResult<ListMenuResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }

            response.isSuccessful -> {
                val listMenu = response.body()
                return NetworkResult.Success(listMenu!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    //=======================================MENU BY CATEGORY=======================================//
    fun getMenuByCategory(category: String) = viewModelScope.launch {
        getMenuByCategorySafeCall(category)
    }

    private suspend fun getMenuByCategorySafeCall(category: String) {
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getMenuByCategory(category)
                listMenuByCategoryResponse.postValue(handleListMenuResponse(response))

            } catch (e: Exception) {
                listMenuByCategoryResponse.value = NetworkResult.Error("Error: $e")

            }
        } else {
            listMenuByCategoryResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    //=======================================CATEGORY=======================================//
    fun getCategory() = viewModelScope.launch {
        getCategorySafeCall()
    }

    private suspend fun getCategorySafeCall() {
        categoryResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getCategoryMenu()
                categoryResponse.value = handleCategoryResponse(response)

                val listCategory = categoryResponse.value!!.data
                if (listCategory != null) {
                    offlineCacheCategory(listCategory)
                }
            } catch (e: Exception) {
                categoryResponse.value = NetworkResult.Error("Error $e")
            }
        } else {
            categoryResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheCategory(listCategory: CategoryResponse) {
        val category = Category(listCategory)
        insertCategory(category)
    }

    private fun handleCategoryResponse(response: Response<CategoryResponse>): NetworkResult<CategoryResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }

            response.isSuccessful -> {
                val listCategory = response.body()
                return NetworkResult.Success(listCategory!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
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