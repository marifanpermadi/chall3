package com.example.chall3.data.apimodel


import com.google.gson.annotations.SerializedName

data class ListMenuResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<DataMenu>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)