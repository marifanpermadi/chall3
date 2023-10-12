package com.example.chall3.ui.register.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class RegisterDataSource {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    suspend fun register(email: String, password: String): Boolean {

        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user != null
        } catch (e: Exception) {
            Log.e("RegisterDataSource", "Registration failed with exception: ${e.message}")
            false
        }
    }
}