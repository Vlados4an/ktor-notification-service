package routes

import dto.notification.NotificationPreferenceRequest
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import service.NotificationService
import util.getIntParamOrBadRequest

fun Route.notificationRoutes() {
    val notificationService by closestDI().instance<NotificationService>()

    authenticate("auth-jwt") {
        route("/api/v1/notifications") {
            post("/preferences") {
                val req = call.receive<NotificationPreferenceRequest>()
                notificationService.setPreferences(req)
                call.respond(mapOf("status" to "ok"))
            }

            get("/my/{userId}") {
                val userId = call.getIntParamOrBadRequest("userId")
                val notifications = notificationService.getMyNotifications(userId)
                call.respond(notifications)
            }
        }
    }
}