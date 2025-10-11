package repository


import dto.notification.NotificationDto
import dto.notification.NotificationPreferenceRequest
import java.util.UUID

interface NotificationRepository {
    fun savePreference(req: NotificationPreferenceRequest)
    fun getUserNotifications(userId: UUID): List<NotificationDto>
    fun addNotification(dto: NotificationDto)
}