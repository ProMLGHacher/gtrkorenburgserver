package com.orenburg.gtrk.routes

import client
import com.orenburg.gtrk.models.weather.RespondWeatherData
import com.orenburg.gtrk.models.weather.WeatherData
import com.orenburg.gtrk.utils.serverUrl
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import stringTolatyn

fun Route.weatherRoute() {
    route("weather") {
        get("/now/{city}") {
            val city = call.parameters["city"].toString()
            val response: WeatherData = client.get("https://api.weatherapi.com/v1/current.json?key=9a518af3180744a5884142441222205&q=${stringTolatyn(city)}&aqi=no&lang=ru").body()
            val respondWeatherData = RespondWeatherData(
                localtime = response.location.localtime,
                icon = getIconFromBoolDayAndCloud(response.current.is_day == "1", response.current.cloud.toInt() > 45),
                temp_c = response.current.temp_c,
                is_day = response.current.is_day == "1",
                humidity = response.current.humidity,
                wind_kph = response.current.wind_kph,
                pressure_mb = response.current.pressure_mb
            )
            call.respond(respondWeatherData)
        }
    }
}

fun getIconFromBoolDayAndCloud(isDay: Boolean, isCloud: Boolean): String {

    if (isDay && isCloud) {
        return serverUrl + "files" + "/sun_cloud.png"
    } else if (isDay) {
        return serverUrl + "files" + "/sun.png"
    }
    if (!isDay && isCloud) {
        return serverUrl + "files" + "/moon_cloud.png"
    } else if (!isDay) {
        return serverUrl + "files" + "/moon.png"
    }

    return ""

}