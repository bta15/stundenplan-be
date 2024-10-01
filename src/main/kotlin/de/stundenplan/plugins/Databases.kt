package de.stundenplan.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    Database.connect(
        "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        "org.h2.Driver",
//        user = "postgres",
//        password = "password"
    )

    transaction {
        create(SCHULE)
    }
}

object SCHULE : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val schulform = varchar("schulform", 50)
    val klassen = varchar("klassen", 50) //TODO
    override val primaryKey = PrimaryKey(id)
}