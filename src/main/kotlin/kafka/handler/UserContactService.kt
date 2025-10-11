package kafka.handler

import dto.notification.NotificationEvent
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import ru.clevertec.kafka.handler.EventHandler
import service.email.EmailService

interface UserContactService {
    fun getEmail(userId: Int): String?
}

class NotificationEventHandler(
    private val userContactService: UserContactService,
    private val emailService: EmailService
) : EventHandler<NotificationEvent> {

    private val logger = LoggerFactory.getLogger(NotificationEventHandler::class.java)
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun handle(eventJson: String) {
        val event = json.decodeFromString(NotificationEvent.serializer(), eventJson)
        val email = userContactService.getEmail(event.userId)

        if (email.isNullOrBlank()) {
            logger.warn("Email не найден для userId={}", event.userId)
            return
        }

        val subject = "[${event.type}] ${event.title}"
        val body = buildString {
            appendLine(event.message)
            appendLine()
            appendLine("Создано: ${event.createdAt}")
            event.sentAt?.let { appendLine("Отправлено: $it") }
        }

        emailService.send(email, subject, body)
        logger.info("Уведомление отправлено: id={}, to={}", event.id, email)
    }
}