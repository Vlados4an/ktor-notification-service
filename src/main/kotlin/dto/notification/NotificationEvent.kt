package dto.notification

import config.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class NotificationEvent(
    val id: Int,
    val userId: Int,
    val type: NotificationEventType,
    val title: String,
    val message: String,
    val isSent: Boolean = false,
    @Serializable(with = InstantSerializer::class)
    val sentAt: Instant? = null,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant
)