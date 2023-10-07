package com.example.chall3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var address: String? = null,
    var detail: String? = null,
    var price: Int? = null,
    var image: String? = null,
    var name: String? = null
) : Parcelable
