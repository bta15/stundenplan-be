package de.stundenplan.plugins

import Schule
import Schulform
import de.stundenplan.model.ISchuleRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting(schuleRepository: ISchuleRepository) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    routing {
        route("/schule") {
            get {
                val schulen = schuleRepository.allSchulen()
                call.respond(schulen)
            }
            get("/byName/{schuleName}") {
                val name = call.parameters["schuleName"]
                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val schule = schuleRepository.schuleByName(name)
                if (schule == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(schule)
            }
            get("/bySchulform/{schulform}") {
                val schulformAsText = call.parameters["schulform"]
                if (schulformAsText == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                try {
                    val schulform = Schulform.valueOf(schulformAsText)
                    val schulen = schuleRepository.schulenBySchulform(schulform)

                    if (schulen.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound)
                        return@get
                    }
                    call.respond(schulen)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            post {
                try {
                    val schule = call.receive<Schule>()
                    schuleRepository.addSchule(schule)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{schuleName}") {
                val name = call.parameters["schuleName"]
                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (schuleRepository.removeSchule(name)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        staticResources("static", "static")

        get("/error-test") {
            throw IllegalStateException("Too Busy")
        }
    }
}
