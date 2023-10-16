package com.example.chall3.database.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartItem: Cart)

    @Query("DELETE FROM cart_items")
    fun deleteALlItems()

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<Cart>>

    @Query("DELETE FROM cart_items WHERE id = :cartId")
    fun deleteById(cartId: Long)

    @Update
    fun update(cartItem: Cart)
}