package com.example.chall3.data.apimodel


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<DataCategory>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)