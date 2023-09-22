package com.example.chall3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Foods(
    val name: String,
    val price: Int,
    val photo: Int,
    val description: String,
    val star: String,
    val address: String
) : Parcelable
