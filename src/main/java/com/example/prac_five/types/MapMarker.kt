package com.example.prac_five.types

data class MapMarker(
    val id: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val userId: String = "",
    val nickname: String = "",
    val avatarUrl: String = "",
    val type: String = "default",
    val title: String = "",
    val description: String = "",
    val createdAt: Long = 0L
)