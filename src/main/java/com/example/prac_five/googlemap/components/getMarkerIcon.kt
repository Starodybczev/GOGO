package com.example.prac_five.googlemap.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.example.prac_five.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlin.collections.getOrPut

fun getMarkerIcon(
    type: String,
    context: Context,
    cache: MutableMap<String, BitmapDescriptor>
): BitmapDescriptor {


    fun vectorToBitmapDescriptor(
        context: Context,
        @DrawableRes resId: Int
    ): BitmapDescriptor {

        val drawable = AppCompatResources.getDrawable(context, resId)
            ?: return BitmapDescriptorFactory.defaultMarker()

        val sizePx = (48 * context.resources.displayMetrics.density).toInt()

        val bitmap = Bitmap.createBitmap(
            sizePx,
            sizePx,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, sizePx, sizePx)
        drawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    return cache.getOrPut(type) {
        when (type) {
            "UNIVERSITY" -> vectorToBitmapDescriptor(context, R.drawable.location_university)
            "CAFE" -> vectorToBitmapDescriptor(context, R.drawable.coffee_shop)
            "SHOP" -> vectorToBitmapDescriptor(context, R.drawable.grocery)
            "MALL" -> vectorToBitmapDescriptor(context, R.drawable.shop)
            "PARK" -> vectorToBitmapDescriptor(context, R.drawable.park)
            "HOTEL" -> vectorToBitmapDescriptor(context, R.drawable.travel)
            "SCHOOL" -> vectorToBitmapDescriptor(context, R.drawable.location_mark)
            "MAIL" -> vectorToBitmapDescriptor(context, R.drawable.email_address)
            else -> BitmapDescriptorFactory.defaultMarker()
        }
    }
}