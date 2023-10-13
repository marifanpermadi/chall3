package com.example.chall3.ui.login.data.model

/**
 * DataCategory class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String
)