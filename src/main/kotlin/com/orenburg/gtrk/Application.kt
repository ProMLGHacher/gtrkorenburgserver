package com.orenburg.gtrk

import com.orenburg.gtrk.database.NewTable
import io.ktor.server.application.*
import com.orenburg.gtrk.plugins.*
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

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    println(formatter.format(LocalDate.now()))
    val current2 = LocalDate.parse("2021-07-29", formatter)
    val current3 = LocalDate.parse("2021-08-29", formatter)
    println(current2 > current3)

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

        NewTable.select { NewTable.type.eq("ergui") } .limit(Int.MAX_VALUE, 0).forEach { println(it) }

    }

    configureRouting()
}
