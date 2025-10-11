// src/main/kotlin/mapper/NotificationMapper.kt
package mapper

import dto.notification.NotificationDto
import model.entity.NotificationEntity

object NotificationMapper {
    fun NotificationEntity.toDto() = NotificationDto(
        id = this.id.value,
        userId = this.userId,
        type = this.type,
        title = this.title,
        message = this.message,
        isSent = this.isSent,
        sentAt = this.sentAt,
        createdAt = this.createdAt
    )
}