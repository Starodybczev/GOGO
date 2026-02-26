package com.example.prac_five.googlemap.features

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlin.math.abs

@Composable
fun rememberSensorRotation(): Float {

    val context = LocalContext.current

    var filteredBearing by remember { mutableStateOf(0f) }
    var lastBearing by remember { mutableStateOf(0f) }

    DisposableEffect(Unit) {

        val sensorManager =
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val rotationSensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {

                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(
                    rotationMatrix,
                    event.values
                )

                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)

                val azimuth =
                    Math.toDegrees(orientation[0].toDouble()).toFloat()

                val newBearing = (azimuth + 360) % 360

                // LOW PASS FILTER
                val alpha = 0.15f
                filteredBearing =
                    lastBearing + alpha * (newBearing - lastBearing)

                if (abs(filteredBearing - lastBearing) > 1f) {
                    lastBearing = filteredBearing
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            listener,
            rotationSensor,
            SensorManager.SENSOR_DELAY_GAME
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    return filteredBearing
}