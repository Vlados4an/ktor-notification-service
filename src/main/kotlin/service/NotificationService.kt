package service

import dto.notification.NotificationDto
import dto.notification.NotificationPreferenceRequest
import java.util.UUID

interface NotificationService {
    fun setPreferences(req: NotificationPreferenceRequest)
    fun getMyNotifications(userId: UUID): List<NotificationDto>
    fun addNotification(dto: NotificationDto)
}