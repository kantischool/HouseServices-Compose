package com.example.houseflat.models

data class MainModel(
    val _id: String,
    val name: List<String>,
    val price: Long,
    val item_taxes: List<Long>,
    val specifications: List<HouseService>
)
