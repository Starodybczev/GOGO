package com.example.prac_five.pages

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.prac_five.components.HeaderSettings
import com.example.prac_five.constElems.AppButton
import com.example.prac_five.constElems.marking.AppColumn
import com.example.prac_five.fireBase.viewmodel.AuthViewModel

@Composable
fun LogOut(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { HeaderSettings(navController) },
    ) { paddingValues ->
        AppColumn(
            modifier = Modifier.padding(paddingValues),
        )
        {
        Text("LogOut", color = MaterialTheme.colorScheme.onBackground)
            AppButton(text = "logOut", onClick = {viewModel.logout(); navController.navigate("auth") { popUpTo("home") { inclusive = true } }} , backgroundColor = Color.Red, textColor = Color.White, borderColor = Color.Red)
        }
    }
}