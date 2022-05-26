package com.orenburg.gtrk.routes

import client
import com.orenburg.gtrk.models.weather.WeatherData
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import stringTolatyn

fun Route.weatherRoute() {
    route("weather") {
        get("/{city}") {
            var city = call.parameters["city"].toString()
            val response: WeatherData = client.get("https://api.weatherapi.com/v1/current.json?key=9a518af3180744a5884142441222205&q=${stringTolatyn(city)}&aqi=no&lang=ru").body()
            print(response.current.temp_c)
            call.respond(response)
        }
    }
}