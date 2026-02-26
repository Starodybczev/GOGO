package com.example.prac_five.constElems

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    val animatedColor by animateColorAsState(
        targetValue = if (selected)
            Color.White.copy(alpha = 0.2f)
        else
            Color.Transparent,
        label = ""
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(animatedColor)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {

        Text(
            text = title,
            color = if (selected) Color.White else Color.LightGray
        )
    }
}