package com.example.chall3.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chall3.ui.login.data.LoginRepository
import com.example.chall3.utils.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<Unit>>()
    val loginResult: LiveData<Result<Unit>> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = loginRepository.login(username, password)

                if (result is Result.Success) {
                    _loginResult.value = Result.Success(Unit)
                } else {
                    _loginResult.value = Result.Error(Exception("Login failed"))
                }
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e)
            }
        }
    }
}