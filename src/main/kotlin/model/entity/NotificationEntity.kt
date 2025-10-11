package model.entity

import model.table.Notifications
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class NotificationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<NotificationEntity>(Notifications)

    var userId by Notifications.userId
    var type by Notifications.type
    var title by Notifications.title
    var message by Notifications.message
    var isSent by Notifications.isSent
    var sentAt by Notifications.sentAt
    var createdAt by Notifications.createdAt
}