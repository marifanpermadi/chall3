package com.example.chall3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chall3.data.Repository
import com.example.chall3.database.users.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val isListView = MutableLiveData<Boolean>().apply { value = true }
    var userLiveData: LiveData<User> = MutableLiveData()

    fun getUserByEmail() {
        val email = repository.remote.getCurrentUser()
        userLiveData = email?.let { repository.local.getUser(it) }!!
    }
}