package com.example.chall3.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chall3.data.Repository
import com.example.chall3.database.users.User
import com.example.chall3.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _registerResult = MutableLiveData<Result<Unit>>()
    val registerResult: LiveData<Result<Unit>> = _registerResult

    fun register(email: String, password: String, userName: String, phoneNumber: String) {
        viewModelScope.launch {
            try {

                when (val result = repository.remote.registerUser(email, password)) {
                    is Result.Success -> {
                        _registerResult.value = Result.Success(Unit)
                        val user = User(email = email, userName = userName, phoneNumber = phoneNumber)
                        insertUser(user)
                    }
                    is Result.Error -> {
                        _registerResult.value = Result.Error(result.exception)
                    }
                }
            } catch (e: Exception) {
                _registerResult.value = Result.Error(e)
            }
        }
    }

    private suspend fun insertUser(user: User) {
       repository.local.insertUser(user)
    }
}