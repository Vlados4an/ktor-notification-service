package dto.notification

import kotlinx.serialization.Serializable
import model.enums.NotificationType
import java.util.UUID

@Serializable
data class NotificationPreferenceRequest(
    val userId: Int,
    val enabledTypes: List<NotificationType>
)