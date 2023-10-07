package com.example.chall3.data.apimodel


import com.google.gson.annotations.SerializedName

data class MenuByCategoryResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<DataByCategory>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)