package com.example.prac_five.constElems

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.example.prac_five.types.InputStyle

@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    style: InputStyle = InputStyle()
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = style.backgroundColor,
            unfocusedContainerColor = style.backgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = style.textColor,
            unfocusedTextColor = style.textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
            .padding(style.padding)
            .border(
                width = style.borderWidth,
                color = style.borderColor,
                shape = RoundedCornerShape(style.cornerRadius)
            )
            .clip(RoundedCornerShape(style.cornerRadius))
    )
}