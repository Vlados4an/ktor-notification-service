package service

import dto.notification.NotificationDto
import dto.notification.NotificationPreferenceRequest

interface NotificationService {
    fun setPreferences(req: NotificationPreferenceRequest)
    fun getMyNotifications(userId: Int): List<NotificationDto>
    fun addNotification(dto: NotificationDto)
}