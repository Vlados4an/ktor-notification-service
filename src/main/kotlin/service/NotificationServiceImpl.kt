package service

import dto.notification.NotificationDto
import dto.notification.NotificationPreferenceRequest
import repository.NotificationRepository

class NotificationServiceImpl(
    private val repo: NotificationRepository
) : NotificationService {

    override fun setPreferences(req: NotificationPreferenceRequest) =
        repo.savePreference(req)

    override fun getMyNotifications(userId: Int): List<NotificationDto> =
        repo.getUserNotifications(userId)

    override fun addNotification(dto: NotificationDto) =
        repo.addNotification(dto)
}