package com.example.chall3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall3.database.users.User
import com.example.chall3.database.users.UserDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel(
    private val userDao: UserDao
) : ViewModel() {
    val isListView = MutableLiveData<Boolean>().apply { value = true }
    var userLiveData: LiveData<User> = MutableLiveData()

    fun getUserByEmail() {
        val user = Firebase.auth.currentUser
        val userEmail = user?.email
        userLiveData = userEmail?.let { userDao.getUser(it) }!!
    }
}