package com.example.prac_five.googlemap.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.google.maps.android.compose.MapType

@Composable
fun MapTypeMenu(
    currentMapType: MapType,
    onMapTypeChange: (MapType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Map menu",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Normal") },
                onClick = {
                    onMapTypeChange(MapType.NORMAL)
                    expanded = false
                },
                trailingIcon = {
                    if (currentMapType == MapType.NORMAL) Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            )

            DropdownMenuItem(
                text = { Text("Satellite") },
                onClick = {
                    onMapTypeChange(MapType.SATELLITE)
                    expanded = false
                },
                trailingIcon = {
                    if (currentMapType == MapType.SATELLITE) Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            )

            DropdownMenuItem(
                text = { Text("Terrain") },
                onClick = {
                    onMapTypeChange(MapType.TERRAIN)
                    expanded = false
                },
                trailingIcon = {
                    if (currentMapType == MapType.TERRAIN) Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            )
        }
    }
}