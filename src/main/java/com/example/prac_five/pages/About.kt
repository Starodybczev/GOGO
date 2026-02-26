package com.example.prac_five.pages

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.prac_five.components.Header
import com.example.prac_five.constElems.Gap
import com.example.prac_five.constElems.marking.AppColumn
import com.example.prac_five.fireBase.viewmodel.AuthViewModel
import com.google.maps.android.compose.MapType

@Composable
fun About(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    var mapType by rememberSaveable { mutableStateOf(MapType.NORMAL) }
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { Header(navController, mapType = mapType , onMapTypeChange = {mapType = it}) }
    ) { paddingValues ->
        AppColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Gap()
            Text("About", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}