package com.example.prac_five.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prac_five.constElems.Gap

@Composable
fun SettingMenu(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp, top = 20.dp, start = 20.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SettingsItem("Toggle Themes", onClick = {navController.navigate("theme")})
            Gap()
            SettingsItem("Update Profile", onClick = {navController.navigate("updateProfile")})
            Gap()
            SettingsItem("Logout", onClick = {navController.navigate("logout")})
            Gap()
            SettingsItem("About US page", onClick = {navController.navigate("about")})
        }
    }
}