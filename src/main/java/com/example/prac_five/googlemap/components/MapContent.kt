package com.example.prac_five.googlemap.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

import com.example.prac_five.fireBase.viewmodel.UserViewModel
import com.example.prac_five.types.MapMarker
import com.example.prac_five.types.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapContent(
    markers: List<MapMarker>,
    hasPermission: Boolean,
    latLng: LatLng?,
    bearing: Float,
    viewModel: UserViewModel,
    currentUser: User?,
    favorites: List<String>,
    mapType: MapType
) {

    val cameraPositionState = rememberCameraPositionState {
        if (latLng != null && latLng.latitude != 0.0) {
            position = CameraPosition.fromLatLngZoom(latLng, 17f)
        }
    }
    val markerState = remember { MarkerState() }

    var followUser by remember { mutableStateOf(true) }
    var firstFix by remember { mutableStateOf(true) }
    var selectedMarker by remember { mutableStateOf<MapMarker?>(null) }

    val sheetState = rememberModalBottomSheetState(true)
    val scope = rememberCoroutineScope()

    var isMapLoaded by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(if (isMapLoaded) 1f else 0f)

    LaunchedEffect(latLng) {
        // 1. Скипаем "Африку" (нулевые координаты)
        if (latLng == null || (latLng.latitude == 0.0 && latLng.longitude == 0.0)) return@LaunchedEffect

        markerState.position = latLng

        if (viewModel.isFirstLaunch) {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(latLng, 17f)
            )
            viewModel.markFirstLaunchDone()
        } else if (followUser) {

            cameraPositionState.animate(
                CameraUpdateFactory.newLatLng(latLng),
                1000
            )
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            viewModel.updateCameraPosition(cameraPositionState.position)
        }
    }

    LaunchedEffect(cameraPositionState.cameraMoveStartedReason) {
        if (cameraPositionState.cameraMoveStartedReason ==
            CameraMoveStartedReason.GESTURE
        ) {
            followUser = false
        }
    }

    Box(Modifier.fillMaxSize()) {

        if (latLng != null && latLng.latitude != 0.0){

        // 🗺 Карта
        MapView(
            markers = markers,
            latLng = latLng,
            bearing = bearing,
            markerState = markerState,
            cameraPositionState = cameraPositionState,
            onMarkerClick = {
                selectedMarker = it
                scope.launch { sheetState.show() }
            },
            mapType = mapType,
            onMapLouded = {isMapLoaded = true},
            onMapLongClick = { newLatLng ->
                currentUser?.let {
                    viewModel.addMarker(newLatLng, it)
                }
            },
            modifier = Modifier.alpha(alpha)
        )
        }else {
            // Показывай тут свой крутой лоадер, пока ждем координаты
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }


        FollowButton(
            visible = !followUser,
            onClick = {
                followUser = true
                latLng?.let {
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(it, 17f)
                    )
                }
            }
        )

            // 🔘 Кнопка возврата

            // 📄 BottomSheet
            selectedMarker?.let { marker ->
                MarkerBottomSheet(
                    marker = marker,
                    currentUser = currentUser,
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            selectedMarker = null
                        }
                    },
                    onSave = { id, title, desc, type ->
                        viewModel.updateMarker(
                            markerId = id,
                            title = title,
                            description = desc,
                            type = type
                        )
                    },
                    onDelate = { id -> viewModel.deleteMarker(id) },
                    favorites = favorites,
                )
            }

    }
}

