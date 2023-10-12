package com.example.chall3.ui.register.data

class RegisterRepository (private val registerDataSource: RegisterDataSource) {

    suspend fun register(email: String, password: String): Boolean {
        return registerDataSource.register(email, password)
    }
}