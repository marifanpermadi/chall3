package com.example.chall3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Foods(
    val name: String,
    val price: Int,
    val photo: Int,
    val description: String,
    //val location: String
) : Parcelable
