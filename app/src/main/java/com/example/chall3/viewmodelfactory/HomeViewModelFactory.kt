package com.example.chall3.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chall3.database.users.UserDao
import com.example.chall3.viewmodel.HomeViewModel

class HomeViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {

}