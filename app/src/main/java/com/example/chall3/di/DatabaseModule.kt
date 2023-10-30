package com.example.chall3.di

import android.content.Context
import androidx.room.Room
import com.example.chall3.database.category.CategoryDatabase
import com.example.chall3.database.menu.MenuDatabase
import com.example.chall3.database.users.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    /** USER DATABASE **/
    @Singleton
    @Provides
    fun provideUserDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_database"
    )

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase) = userDatabase.userDao()


    /** CATEGORY DATABASE **/
    @Singleton
    @Provides
    fun provideCategoryDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CategoryDatabase::class.java,
        "category_database"
    ).build()

    @Singleton
    @Provides
    fun provideCategoryDao(categoryDatabase: CategoryDatabase) = categoryDatabase.categoryDao()


    /** MENU DATABASE **/
    @Singleton
    @Provides
    fun provideMenuDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MenuDatabase::class.java,
        "menu_database"
    ).build()

    @Singleton
    @Provides
    fun provideMenuDao(database: MenuDatabase) = database.menuDao()
}