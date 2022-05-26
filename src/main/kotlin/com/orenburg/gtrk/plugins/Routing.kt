package com.orenburg.gtrk.plugins

import com.orenburg.gtrk.models.DetailedNew
import com.orenburg.gtrk.routes.addNew
import com.orenburg.gtrk.routes.newRoute
import com.orenburg.gtrk.routes.weatherRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(DetailedNew())
        }

        newRoute()

        addNew()
        weatherRoute()
    }
}
