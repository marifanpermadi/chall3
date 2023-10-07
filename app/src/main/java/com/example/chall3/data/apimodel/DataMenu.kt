package com.example.chall3.data.apimodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataMenu(
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
) : Parcelable