package com.example.chall3.data.apimodel

data class OrderItem(
    val nama: String,
    val qty: Int,
    val catatan: String ?= null,
    val harga: Int
)

data class OrderRequest(
    val username: String,
    val total: Int,
    val orders: List<OrderItem>
)
