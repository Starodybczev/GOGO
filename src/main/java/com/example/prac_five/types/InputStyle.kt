package com.example.prac_five.types

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



data class InputStyle(
    val backgroundColor: Color = Color.White,
    val textColor: Color = Color.Black,
    val borderColor: Color = Color(0xFF2E7D32), // зелёный
    val borderWidth: Dp = 2.dp,
    val cornerRadius: Dp = 12.dp,
    val padding: Dp = 15.dp
)