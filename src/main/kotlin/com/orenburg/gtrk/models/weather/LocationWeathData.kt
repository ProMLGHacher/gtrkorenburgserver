package com.orenburg.gtrk.models.weather

data class LocationWeathData(
    val name: String,
    val region: String,
    val country: String,
    val lat: String,
    val lon: String,
    val tz_id: String,
    val localtime_epoch: String,
    val localtime: String
)
