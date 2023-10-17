package com.example.chall3.ui.login.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.example.chall3.utils.Result
import kotlinx.coroutines.tasks.await

class LoginDataSource {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    suspend fun login(email: String, password: String): Result<AuthResult> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}