package com.example.chall3.ui.login.data
import com.example.chall3.utils.Result

import com.example.chall3.ui.login.data.model.LoggedInUser
import com.google.firebase.auth.AuthResult

class LoginRepository(val dataSource: LoginDataSource) {

    suspend fun login(username: String, password: String): Result<AuthResult> {
        return dataSource.login(username, password)
    }
}