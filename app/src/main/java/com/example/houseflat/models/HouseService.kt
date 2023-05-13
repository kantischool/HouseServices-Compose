package com.example.houseflat.models

data class HouseService(
    val _id: String,
    val isAssociated: Boolean,
    val is_required: Boolean,
    val list: List<ListItem>,
    val max_range: Int,
    val modifierGroupId: String,
    val modifierGroupName: String,
    val modifierId: String,
    val modifierName: String,
    val name: List<String>,
    val range: Int,
    val sequence_number: Int,
    val type: Int,
    val unique_id: Int,
    val user_can_add_specification_quantity: Boolean
)