package kafka.handler

import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import ru.clevertec.dto.dto.NotificationEvent
import ru.clevertec.kafka.handler.EventHandler
import service.contact.UserContactService
import service.email.EmailService
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NotificationEventHandler(
    private val userContactService: UserContactService,
    private val emailService: EmailService
) : EventHandler<NotificationEvent> {

    private val logger = LoggerFactory.getLogger(NotificationEventHandler::class.java)
    private val json = Json { ignoreUnknownKeys = true }
    private val dateFormatter = DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm:ss")
        .withZone(ZoneId.systemDefault())

    override suspend fun handle(eventJson: String) {
        try {
            val event = json.decodeFromString(NotificationEvent.serializer(), eventJson)
            val email = userContactService.getEmail(event.reservedBy)

            if (email.isNullOrBlank()) {
                logger.warn("Email not found for userId={}", event.reservedBy)
                return
            }

            val subject = "Book Reservation Notification"
            val formattedDate = dateFormatter.format(Instant.ofEpochMilli(event.timestamp))
            
            val body = """
                Hello,
                
                ${event.message}
                
                Book ID: ${event.bookId}
                Reserved Until: ${event.reservedUntil}
                
                This notification was generated on: $formattedDate
                
                Best regards,
                Your Notification Service
            """.trimIndent()

            emailService.send(email, subject, body)
            logger.info("Notification sent successfully to: {}", email)
        } catch (e: Exception) {
            logger.error("Error processing notification event: {}", e.message, e)
        }
    }
}