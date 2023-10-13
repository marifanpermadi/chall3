package com.example.chall3.data.apimodel


import com.google.gson.annotations.SerializedName

data class DataCategory(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("nama")
    val nama: String
)