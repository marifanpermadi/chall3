package com.example.chall3.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartItem: Cart)

    @Delete
    fun delete(cartItem: Cart)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<Cart>>

}