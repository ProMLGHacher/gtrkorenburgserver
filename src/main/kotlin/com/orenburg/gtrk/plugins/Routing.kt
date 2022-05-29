package com.orenburg.gtrk.plugins

import com.orenburg.gtrk.routes.addNew
import com.orenburg.gtrk.routes.newRoute
import com.orenburg.gtrk.routes.weatherRoute
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond("Хороший сервер")
        }

        static("/files") {
            File("C:/serverFolder/").mkdir()
            staticRootFolder = File("C:/serverFolder")
            files(".")
        }

        static("/weatherFiles") {
            staticRootFolder = File("files/")
            files(".")
        }

        newRoute()

        addNew()
        weatherRoute()
    }
}
