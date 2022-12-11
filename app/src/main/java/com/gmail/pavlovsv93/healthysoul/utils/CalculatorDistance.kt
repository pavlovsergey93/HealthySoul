package com.gmail.pavlovsv93.healthysoul.utils

import android.util.Log
import com.google.firebase.firestore.GeoPoint
import kotlin.math.pow

fun calculatorDistance(geo1: GeoPoint, geo2: GeoPoint) : Double {
    var distance: Double = 0.0
    val x1 = geo1.longitude
    val y1 = geo1.latitude
    val x2 = geo2.longitude
    val y2 = geo2.latitude

    val R = 6378.137;
    val dLat = y2 * Math.PI / 180 - y1 * Math.PI / 180;
    val dLon = x2 * Math.PI / 180 - x1 * Math.PI / 180;
    val a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(y1 * Math.PI / 180) * Math.cos(y2 * Math.PI / 180) * Math.sin(dLon/2) * Math.sin(dLon/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    distance = R * c;
    Log.d("WWW.Distance", "Distance: $distance")
    return distance
}