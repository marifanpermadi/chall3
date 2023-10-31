package com.example.chall3.database.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1)
abstract class CartDataBase : RoomDatabase() {

    abstract fun cartDao(): CartDao
}
