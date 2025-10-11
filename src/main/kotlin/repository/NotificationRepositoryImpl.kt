package repository

import dto.notification.NotificationDto
import dto.notification.NotificationPreferenceRequest
import mapper.NotificationMapper.toDto
import model.entity.NotificationEntity
import model.enums.NotificationType
import model.table.Notifications
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.concurrent.ConcurrentHashMap

class NotificationRepositoryImpl : NotificationRepository {

    private val preferences = ConcurrentHashMap<Int, Set<NotificationType>>()

    override fun savePreference(req: NotificationPreferenceRequest) {
        preferences[req.userId] = req.enabledTypes.toSet()
    }

    override fun getUserNotifications(userId: Int): List<NotificationDto> = transaction {
        NotificationEntity
            .find { Notifications.userId eq userId }
            .orderBy(Notifications.createdAt to SortOrder.DESC)
            .map { it.toDto() }
    }

    override fun addNotification(dto: NotificationDto) {
        val allowed = preferences[dto.userId]
        if (allowed != null && dto.type !in allowed) {
            return
        }

        transaction {
            NotificationEntity.findById(dto.id) ?: NotificationEntity.new(id = dto.id) {
                userId = dto.userId
                type = dto.type
                title = dto.title
                message = dto.message
                isSent = dto.isSent
                sentAt = dto.sentAt
                createdAt = dto.createdAt
            }
        }
    }
}