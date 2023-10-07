package com.example.chall3.data.apimodel


import com.google.gson.annotations.SerializedName

data class DataByCategory(
    @SerializedName("alamat_resto")
    val alamatResto: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("harga")
    val harga: Int,
    @SerializedName("harga_format")
    val hargaFormat: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("nama")
    val nama: String
)