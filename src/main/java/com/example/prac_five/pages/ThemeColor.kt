package com.example.prac_five.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.prac_five.components.HeaderSettings
import com.example.prac_five.constElems.AppButton
import com.example.prac_five.constElems.marking.AppColumn


@Composable
fun ThemeColor(navController: NavHostController, onToggleTheme: () -> Unit) {

     val DarkColors = darkColorScheme(
        background = Color(0xFF2E2E2E),
        onBackground = Color(0xFFF6F6F6),

         surfaceVariant = Color(0xFF444444),
         onSurfaceVariant = Color(0xFFF6F6F6)

    )

     val LightColors = lightColorScheme(
        background = Color(0xFFF6F6F6),
        onBackground = Color(0xFF2E2E2E),

         surfaceVariant = Color(0xFFFFFFFF),
         onSurfaceVariant = Color(0xFF2E2E2E)
    )

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { HeaderSettings(navController) },
    ) { paddingValues ->
        AppColumn(
            modifier = Modifier.padding(paddingValues),

        )
        {
            Text("Theme Color", color = MaterialTheme.colorScheme.onBackground)
            AppButton(text = "toggle theme", onClick = {onToggleTheme()})
        }
    }
}