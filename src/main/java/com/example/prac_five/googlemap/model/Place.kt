package com.example.prac_five.googlemap.model

data class Place(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val category: String = "",
    val detailsUrl: String = ""
)