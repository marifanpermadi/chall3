package com.example.chall3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuCategory(
    val name: String,
    val image: Int
) : Parcelable
