package com.example.prac_five.types

import android.net.Uri

data class FormStateRegister(
    val name: String = "",
    val email: String = "",
    val photoURL : String = "",
    val photoUri: Uri? = null,
    val password: String = "",
    val configurePassword: String = ""
)
