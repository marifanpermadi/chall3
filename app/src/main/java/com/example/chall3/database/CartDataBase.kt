package com.example.chall3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 3)
abstract class CartDataBase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        private var INSTANCE: CartDataBase? = null

        @JvmStatic
        fun getDataBase(context: Context): CartDataBase {
            if (INSTANCE == null) {
                synchronized(CartDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CartDataBase::class.java, "cart_database"
                    )
                        //.addMigrations(Migration2to3())
                        .build()
                }
            }
            return INSTANCE as CartDataBase
        }
    }
}