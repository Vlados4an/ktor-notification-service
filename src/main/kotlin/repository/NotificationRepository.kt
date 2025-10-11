package repository


import dto.notification.NotificationDto
import dto.notification.NotificationPreferenceRequest

interface NotificationRepository {
    fun savePreference(req: NotificationPreferenceRequest)
    fun getUserNotifications(userId: Int): List<NotificationDto>
    fun addNotification(dto: NotificationDto)
}