package com.example.chall3.database.users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    /*companion object {
        private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getUserDataBase(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "user_database"
                    )
                        .build()
                }
            }
            return INSTANCE as UserDatabase
        }
    }*/
}

/**
 * @Database(
entities = [Menu::class],
version = 1,
exportSchema = false
)
@TypeConverters(MenuTypeConverter::class)
abstract class MenuDatabase: RoomDatabase() {

abstract fun menuDao(): MenuDao
}
 *
 *
 *
 * **/