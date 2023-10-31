package com.example.chall3.database.cart

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1)
abstract class CartDataBase : RoomDatabase() {

    abstract fun cartDao(): CartDao
}
