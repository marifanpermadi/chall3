package com.example.chall3.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart_items")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var foodImage: Int,
    var foodName: String,
    var foodPrice: Int,
    var orderNote: String ? = null,
    var orderAmount: Int
) : Parcelable
