package com.orenburg.gtrk.database

import org.jetbrains.exposed.sql.Table

object NewTable: Table() {
    val id = integer("id").autoIncrement()
    val type = varchar("type", 50)
    val header = varchar("header", 500)
    val date = varchar("date", 100)
    val urlImage = varchar("urlImage", 1000).nullable()
    val article = text("article")
    val urlVideo = varchar("urlVideo", 1000).nullable()

    override val primaryKey = PrimaryKey(id)
}