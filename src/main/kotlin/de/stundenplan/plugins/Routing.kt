package de.stundenplan.plugins

import Schule
import Schulform
import de.stundenplan.model.SchuleRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    install(StatusPages) {
        exception<IllegalStateException> { call, cause ->
            call.respondText("App in illegal state as ${cause.message}")
        }
    }

    routing {
        staticResources("static", "static")

        route("/schule") {
            get {
                val schulen = SchuleRepository.allSchulen()
                call.respond(schulen)
            }
            get("/byName/{schuleName}") {
                val name = call.parameters["schuleName"]
                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val schule = SchuleRepository.schuleByName(name)
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
                    val schulen = SchuleRepository.schulenBySchulform(schulform)

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
                    SchuleRepository.addSchule(schule)
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

                if (SchuleRepository.removeSchule(name)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        get("/error-test") {
            throw IllegalStateException("Too Busy")
        }

//        // Static plugin. Try to access `/static/index.html`
//        static("/static") {
//            resources("static")
//        }
//
//        staticResources("/demo-ui", "demo-ui")
    }
}
