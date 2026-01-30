package com.example.gpslocationonmap

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@Composable
fun rememberLocationUpdates(
    hasPermission: Boolean
): State<LatLng?> {
    val context = LocalContext.current
    val locationState = remember { mutableStateOf<LatLng?>(null) }
    
    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            getLocationUpdates(context).collect { location ->
                locationState.value = LatLng(location.latitude, location.longitude)
            }
        }
    }
    
    return locationState
}

@SuppressLint("MissingPermission")
private fun getLocationUpdates(context: Context): Flow<Location> = callbackFlow {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    
    // Try multiple priority levels for faster results
    val highAccuracyRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        2000L // 2 seconds - faster updates
    ).apply {
        setMinUpdateDistanceMeters(5f) // Update when moved 5 meters
        setMaxUpdateDelayMillis(5000L) // Max 5 seconds delay
        setMinUpdateIntervalMillis(1000L) // Minimum 1 second between updates
        setWaitForAccurateLocation(false) // Don't wait for high accuracy, get location faster
    }.build()
    
    // Fallback request with lower accuracy but faster response
    val balancedRequest = LocationRequest.Builder(
        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
        1000L // 1 second
    ).apply {
        setMinUpdateDistanceMeters(10f)
        setMaxUpdateDelayMillis(3000L)
        setWaitForAccurateLocation(false)
    }.build()
    
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { location ->
                trySend(location)
            }
        }
    }
    
    // Strategy 1: Get last known location first for immediate display
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let { 
            trySend(location)
        }
    }.addOnFailureListener {
        // If no last known location, create a mock location for testing
        val mockLocation = Location("mock").apply {
            latitude = 37.7749  // San Francisco
            longitude = -122.4194
            accuracy = 10f
            time = System.currentTimeMillis()
        }
        trySend(mockLocation)
    }
    
    // Strategy 2: Get current location immediately (one-time request)
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener { location ->
        location?.let { trySend(it) }
    }.addOnFailureListener {
        // Fallback for emulator - create mock location after 3 seconds
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
            delay(3000)
            val mockLocation = Location("emulator").apply {
                latitude = 37.7749 + (Math.random() - 0.5) * 0.01  // Small random offset
                longitude = -122.4194 + (Math.random() - 0.5) * 0.01
                accuracy = 15f
                time = System.currentTimeMillis()
            }
            trySend(mockLocation)
        }
    }
    
    // Strategy 3: Also try balanced accuracy for faster initial fix
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
        null
    ).addOnSuccessListener { location ->
        location?.let { trySend(it) }
    }
    
    // Strategy 4: Start continuous location updates with high accuracy
    fusedLocationClient.requestLocationUpdates(
        highAccuracyRequest,
        locationCallback,
        null
    )
    
    awaitClose {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}