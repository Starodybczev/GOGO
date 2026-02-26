package com.example.prac_five.constElems

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Gap(
    modifier: Modifier = Modifier,
    height: Dp? = null,
    width: Dp? = null,
    ){
    var finalModifier = modifier

    height?.let {
        finalModifier = finalModifier.height(it)
    }

    width?.let {
        finalModifier = finalModifier.width(it)
    }

    Spacer(modifier = modifier.height(20.dp))
}
