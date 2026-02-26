package com.example.prac_five.googlemap

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prac_five.fireBase.viewmodel.UserViewModel
import com.example.prac_five.googlemap.components.MapContent
import com.example.prac_five.googlemap.features.rememberLocationPermission
import com.example.prac_five.googlemap.features.rememberSensorRotation
import com.example.prac_five.googlemap.features.rememberUserLocation
import com.google.maps.android.compose.MapType


@Composable
fun MapScreen(mapType: MapType) {

    val hasPermission = rememberLocationPermission()
    val latLng = rememberUserLocation(hasPermission)
    val bearing = rememberSensorRotation()
    val viewModel: UserViewModel = viewModel()
    val markers by viewModel.markers.collectAsState()
    val currentUser by viewModel.user.collectAsState()


    val favorites by viewModel.favorites.collectAsState()

    LaunchedEffect(currentUser?.id) {
        currentUser?.id?.let {
            viewModel.listenFavorites(it)
        }
    }

    MapContent(
        markers = markers,
        hasPermission = hasPermission,
        latLng = latLng,
        bearing = bearing,
        viewModel = viewModel,
        currentUser = currentUser,
        favorites = favorites,
        mapType = mapType
    )
}