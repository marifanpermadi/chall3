package com.example.chall3.data

import androidx.lifecycle.LiveData
import com.example.chall3.database.category.Category
import com.example.chall3.database.category.CategoryDao
import com.example.chall3.database.menu.Menu
import com.example.chall3.database.menu.MenuDao
import com.example.chall3.database.users.User
import com.example.chall3.database.users.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val menuDao: MenuDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao
) {

    fun readMenu(): Flow<List<Menu>> {
        return menuDao.readMenu()
    }

    suspend fun insertMenu(menu: Menu) {
        menuDao.insertMenu(menu)
    }

    fun readCategory(): Flow<List<Category>> {
        return categoryDao.readCategory()
    }

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    fun getUser(email: String) : LiveData<User> {
        return userDao.getUser(email)
    }
}