package model.table

import model.enums.NotificationType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Notifications : IntIdTable("notification") {
    val userId = integer("user_id")
    val type = enumerationByName("type", 30, NotificationType::class)
    val title = varchar("title", 255)
    val message = text("message")
    val isSent = bool("is_sent").default(false)
    val sentAt = date("sent_at").nullable()
    val createdAt = date("created_at")
}