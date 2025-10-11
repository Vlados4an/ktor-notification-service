package dto.notification

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import model.enums.NotificationType
import java.time.LocalDate

@Serializable
data class NotificationDto(
    val id: Int,
    val userId: Int,
    val type: NotificationType,
    val title: String,
    val message: String,
    val isSent: Boolean,
    @Contextual val sentAt: LocalDate?,
    @Contextual val createdAt: LocalDate
)