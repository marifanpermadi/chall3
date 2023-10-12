package com.example.chall3.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chall3.ui.register.data.RegisterRepository
import com.example.chall3.utils.Result
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerResult = MutableLiveData<Result<Unit>>()
    val registerResult: LiveData<Result<Unit>> = _registerResult

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val registerSuccessful = registerRepository.register(email, password)

                if (registerSuccessful) {
                    _registerResult.value = Result.Success(Unit)
                } else {
                    _registerResult.value = Result.Error(Exception("Registration failed"))
                }
            } catch (e: Exception) {
                _registerResult.value = Result.Error(e)
            }
        }
    }
}