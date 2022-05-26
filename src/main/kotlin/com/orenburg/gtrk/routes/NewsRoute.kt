package com.orenburg.gtrk.routes

import com.orenburg.gtrk.database.NewTable
import com.orenburg.gtrk.models.*
import com.orenburg.gtrk.utils.formatter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Route.newRoute() {
    route("news"){

        get("/{id}") {
            var id = call.parameters["id"]!!.toInt()

            var req = DetailedNew()

            transaction {
                val new = NewTable.select {NewTable.id.eq(id)}.single()
                req = DetailedNew(new[NewTable.id], NewsType.valueOf(new[NewTable.type]), new[NewTable.header], new[NewTable.date], new[NewTable.urlImage], new[NewTable.article], new[NewTable.urlVideo])
            }

            call.respond(req)

        }

        get {
            val offset = call.request.queryParameters["offset"]?.toLong() ?: 0
            val count = call.request.queryParameters["count"]?.toInt() ?: Int.MAX_VALUE
            val filter = call.request.queryParameters["filter"] ?: "All"
            val date = call.request.queryParameters["date"]

            val currentFilterDate = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter)
            val filterDate = if (date != null) getDateFilter(DateType.valueOf(date), currentFilterDate) else null

            var listNewsEvent = mutableListOf<NewsEvent>()

            transaction {
                val select = if (filter == "All") NewTable.selectAll().limit(count, offset = offset) else NewTable.select{ NewTable.type.eq(filter.toString()) }.limit(count, offset = offset)
                select.forEach { resultRow ->
                    if (filterDate == null) {
                        listNewsEvent += NewsEvent(resultRow[NewTable.id].toString(), NewsType.valueOf(resultRow[NewTable.type]), resultRow[NewTable.header], resultRow[NewTable.date], resultRow[NewTable.urlImage])
                    } else {
                        if (LocalDateTime.parse(resultRow[NewTable.date], formatter) < currentFilterDate && LocalDateTime.parse(resultRow[NewTable.date], formatter) > filterDate) {
                            listNewsEvent += NewsEvent(resultRow[NewTable.id].toString(), NewsType.valueOf(resultRow[NewTable.type]), resultRow[NewTable.header], resultRow[NewTable.date], resultRow[NewTable.urlImage])
                        }
                    }
                }
            }

            call.respond(listNewsEvent)

        }
    }
}

fun getDateFilter(dateType: DateType, currentFilterDate: LocalDateTime) = when(dateType) {
        DateType.AllTime -> null
        DateType.Day -> currentFilterDate.minusDays(1)
        DateType.Hour -> currentFilterDate.minusHours(1)
        DateType.Month -> currentFilterDate.minusMonths(1)
        DateType.Week -> currentFilterDate.minusWeeks(1)
        DateType.Year -> currentFilterDate.minusYears(1)
    }