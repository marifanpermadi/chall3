package com.example.chall3.data

import com.example.chall3.data.api.ApiService
import com.example.chall3.data.apimodel.CategoryResponse
import com.example.chall3.data.apimodel.ListMenuResponse
import com.example.chall3.data.apimodel.OrderRequest
import com.example.chall3.data.apimodel.OrderResponse
import com.example.chall3.utils.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun getListMenu(): Response<ListMenuResponse> {
        return apiService.getListMenuDI()
    }

    suspend fun getCategoryMenu(): Response<CategoryResponse> {
        return apiService.getCategoryDI()
    }

    suspend fun getMenuByCategory(category: String): Response<ListMenuResponse> {
        return apiService.getMenuByCategoryDI(category)
    }

    suspend fun placeOrder(orderRequest: OrderRequest): Response<OrderResponse> {
        return apiService.placeOrderDI(orderRequest)
    }

    suspend fun registerUser(email: String, password: String): Result<AuthResult> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun signInUser(email: String, password: String): Result<AuthResult> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.email
    }

}