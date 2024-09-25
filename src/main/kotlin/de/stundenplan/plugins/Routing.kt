package de.stundenplan.plugins

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
    }
}
