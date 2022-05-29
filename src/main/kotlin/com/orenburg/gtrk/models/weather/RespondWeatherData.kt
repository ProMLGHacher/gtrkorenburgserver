package com.orenburg.gtrk.models.weather

data class RespondWeatherData(
    val localtime: String,
    val icon: String,
    val temp_c: String,
    val is_day: Boolean, //bool
    val humidity: String,
    val wind_kph: String,
    val pressure_mb: String
)
