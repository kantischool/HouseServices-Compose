package com.example.houseflat.models

data class ListItem(
    val _id: String,
    val is_default_selected: Boolean,
    val name: List<String>,
    val price: Int,
    val sequence_number: Int,
    val specification_group_id: String,
    val unique_id: Int
)