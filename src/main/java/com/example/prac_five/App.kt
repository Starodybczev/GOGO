package com.example.prac_five

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.prac_five.components.Header
import com.example.prac_five.googlemap.MapScreen
import com.google.maps.android.compose.MapType


@Composable
fun App(navController: NavHostController) {
    var mapType by rememberSaveable { mutableStateOf(MapType.NORMAL) }
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { Header(navController, mapType = mapType , onMapTypeChange = {mapType = it}) },
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            MapScreen(mapType = mapType)
        }
    }
}

