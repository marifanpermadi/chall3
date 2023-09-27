package com.example.chall3.database

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Cart(
    var foodName: String
) : Parcelable
