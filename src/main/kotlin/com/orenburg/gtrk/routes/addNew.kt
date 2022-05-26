package com.orenburg.gtrk.routes

import com.orenburg.gtrk.database.NewTable
import com.orenburg.gtrk.models.DetailedNew
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

fun Route.addNew() {
    route("addNew") {
        put {
            val new = call.receive<DetailedNew>()

            transaction {
                NewTable.insert {
                    it[type] = new.type.toString()
                    it[header] = new.header
                    it[date] = new.date
                    it[article] = new.article
                    it[urlVideo] = new.urlVideo
                    it[urlImage] = new.urlImage
                }
            }
            call.respond(HttpStatusCode.OK)
        }
        put("/uploadImage") {
            val multipartData = call.receiveMultipart()

            var fileDescription = ""
            var fileName = ""

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        var fileBytes = part.streamProvider().readBytes()
                        File("C:/serverFolder/").mkdir()
                        File("C:/serverFolder/$fileName").writeBytes(fileBytes)
                    }
                }
            }


            if (File("C:/serverFolder/$fileName").exists())
                call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.NotImplemented)

        }
        put("/uploadVideo") {
            val multipartData = call.receiveMultipart()

            var fileDescription = ""
            var fileName = ""

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        var fileBytes = part.streamProvider().readBytes()
                        File("C:/serverFolder/").mkdir()
                        File("C:/serverFolder/$fileName").writeBytes(fileBytes)
                    }
                }
            }


            if (File("C:/serverFolder/$fileName").exists())
                call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.NotImplemented)

        }
    }
}