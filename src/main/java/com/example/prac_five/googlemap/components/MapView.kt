package com.example.prac_five.googlemap.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import com.example.prac_five.types.MapMarker
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapView(
    markers: List<MapMarker>,
    latLng: LatLng?,
    bearing: Float,
    markerState: MarkerState,
    cameraPositionState: CameraPositionState,
    onMarkerClick: (MapMarker) -> Unit,
    onMapLongClick: (LatLng) -> Unit,
    onMapLouded: () -> Unit,
    modifier: Modifier,
    mapType: MapType
) {

    val iconCache = remember { mutableMapOf<String, BitmapDescriptor>() }
    val context = LocalContext.current


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false, mapType = mapType),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false
        ),
        onMapLongClick = onMapLongClick,
    ) {

        if (latLng != null) {
            Marker(
                state = markerState,
                icon = BitmapDescriptorFactory.defaultMarker(),
                rotation = bearing,
                flat = true,
                anchor = Offset(0.5f, 0.5f)
            )
        }

        markers.forEach { marker ->

            key(marker.id) {
                Marker(
                    state = remember {
                        MarkerState(position = LatLng(marker.lat, marker.lng))
                    },
                    icon = getMarkerIcon(marker.type, context, iconCache),
                    onClick = {
                        onMarkerClick(marker)
                        true
                    }
                )
            }
        }
    }
}


