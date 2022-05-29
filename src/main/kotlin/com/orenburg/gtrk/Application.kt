package com.orenburg.gtrk

import com.orenburg.gtrk.database.NewTable
import io.ktor.server.application.*
import com.orenburg.gtrk.plugins.*
import com.orenburg.gtrk.utils.formatter
import initClient
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    println(formatter.format(LocalDateTime.now()))

    initClient()

    install(ContentNegotiation) {
        gson()
    }

    Database.connect(
        "jdbc:mysql://localhost:3306/", driver = "com.mysql.cj.jdbc.Driver",
        user = "root", password = "cf6cf6cf6", databaseConfig =
        DatabaseConfig.invoke{

            this.sqlLogger = CompositeSqlLogger()
            this.defaultSchema = Schema("dtbase")

        })

    transaction {
        SchemaUtils.create(NewTable)
    }

    configureRouting()
}
