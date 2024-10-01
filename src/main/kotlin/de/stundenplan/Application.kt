package de.stundenplan

import de.stundenplan.model.SchuleRepository
import de.stundenplan.plugins.configureDatabases
import de.stundenplan.plugins.configureRouting
import de.stundenplan.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val schuleRepository = SchuleRepository()
    configureSerialization(schuleRepository)
    configureDatabases()
    configureRouting()
}
