package dto.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NotificationEventType {
    @SerialName("RENTAL_REMINDER") RENTAL_REMINDER,
    @SerialName("RENTAL_OVERDUE")  RENTAL_OVERDUE,
    @SerialName("NEW_BOOK")        NEW_BOOK,
    @SerialName("PENALTY")         PENALTY
}