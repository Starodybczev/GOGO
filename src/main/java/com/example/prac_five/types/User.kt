package com.example.prac_five.types

import android.net.Uri
import com.google.firebase.Timestamp

data class User(
    val id: String = "",
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val photoURL: String = "",
    val createdAt: Timestamp? = null
)