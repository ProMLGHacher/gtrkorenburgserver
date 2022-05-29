package com.orenburg.gtrk.routes

import com.orenburg.gtrk.database.NewTable
import com.orenburg.gtrk.models.AddNew
import com.orenburg.gtrk.utils.formatter
import com.orenburg.gtrk.utils.imageUrl
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.io.File
import java.time.LocalDateTime

fun Route.addNew() {
    route("news") {
        post("new") {
            val new = call.receive<AddNew>()
            println(new)
            var i: InsertStatement<Number> = InsertStatement(NewTable)
            transaction {
                i = NewTable.insert {
                    it[type] = new.type.toString()
                    it[header] = new.header
                    it[date] = LocalDateTime.now().format(formatter)
                    it[article] = new.text
                }
            }
            call.respondText(i[NewTable.id].toString())
        }
        post("/{id}/image") {
            val multipartData = call.receiveMultipart()
            println(multipartData)
            var id = call.parameters["id"]!!.toInt()

            var fileDescription = ""
            var fileName = ""

            multipartData.forEachPart { part ->
                println(part)
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                        println(fileDescription)
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        println(fileName)
                        var fileBytes = part.streamProvider().readBytes()
                        println(fileBytes)
                        File("C:/serverFolder").mkdir()
                        File("C:/serverFolder/$id").mkdir()
                        File("C:/serverFolder/$id/$fileName").writeBytes(fileBytes)
                        part.dispose()
                    }
                }
            }

            transaction {
                NewTable.update({ NewTable.id.eq(id) }) {
                    it[NewTable.urlImage] = "$imageUrl/$id/$fileName"
                }
            }


            if (File("C:/serverFolder/$id/$fileName").exists())
                call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.NotImplemented)

        }
    }
}