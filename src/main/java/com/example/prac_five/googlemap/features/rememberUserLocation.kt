package com.example.prac_five.googlemap.features

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng

@Composable
fun rememberUserLocation(hasPermission: Boolean): LatLng? {
    val context = LocalContext.current
    var currentLatLng by remember { mutableStateOf<LatLng?>(null) }

    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val request = remember {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L).build()
    }

    DisposableEffect(hasPermission) {
        if (!hasPermission) return@DisposableEffect onDispose {}

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return@DisposableEffect onDispose {}
        }

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let {
                    val newLatLng = LatLng(it.latitude, it.longitude)
                    // Оптимизация: обновляем стейт только если координаты реально изменились
                    if (currentLatLng != newLatLng) {
                        currentLatLng = newLatLng
                    }
                }
            }
        }

        fusedClient.requestLocationUpdates(request, callback, Looper.getMainLooper())

        onDispose {
            fusedClient.removeLocationUpdates(callback)
        }
    }

    return currentLatLng
}
