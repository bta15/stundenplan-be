package de.stundenplan.plugins

import Klasse
import Schule
import Schulform
import Unterrichtsfach
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }

        get("/irgendwas") {
            val text = "<h1>hier ist der irgendwas-Endpunkt</h1>"
            val type = ContentType.parse("text/html")
            call.respondText(text, type)
        }

        get("/stundenplan") {

            var fach = Unterrichtsfach("Deutsch", 3)
            var klasse = Klasse(1, "1a", listOf(fach))
            val schule = Schule("Meine Schule", Schulform.Grundschule, listOf(klasse))

            val text = "hier wird der Stundenplan sein!"
            val type = ContentType.parse("text/html")

//            call.respondText(text, type)
            call.respond(schule)
        }
    }
}
