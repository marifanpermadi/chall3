package com.example.chall3.database.users

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var email: String,
    val userName: String,
    var phoneNumber: String,
) : Parcelable
