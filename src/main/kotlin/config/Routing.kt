package ru.clevertec.config

import io.ktor.server.application.*
import io.ktor.server.routing.*
import routes.notificationRoutes

fun Application.configureRouting() {
    routing {
        notificationRoutes()
    }
}
