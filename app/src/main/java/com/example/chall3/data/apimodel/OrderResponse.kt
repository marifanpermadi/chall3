package com.example.chall3.data.apimodel


import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)