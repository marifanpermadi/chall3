package com.example.chall3.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chall3.data.Repository
import com.example.chall3.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _loginResult = MutableLiveData<Result<Unit>>()
    val loginResult: LiveData<Result<Unit>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {

                when (val result = repository.remote.signInUser(email, password)) {
                    is Result.Success -> {
                        _loginResult.value = Result.Success(Unit)
                    }

                    is Result.Error -> {
                        _loginResult.value = Result.Error(result.exception)
                    }
                }
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e)
            }
        }
    }

    fun logout() {
        repository.remote.signOut()
    }
}