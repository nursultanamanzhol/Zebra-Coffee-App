package com.example.zebracoffee.presentation.coffeeShops.map

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}