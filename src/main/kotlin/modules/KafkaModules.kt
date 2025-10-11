package ru.clevertec.modules

import dto.notification.NotificationEvent
import io.ktor.server.application.*
import kafka.consumer.KafkaConsumerService
import kafka.consumer.KafkaConsumerServiceImpl
import kafka.handler.NotificationEventHandler
import kafka.handler.UserContactService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import ru.clevertec.config.KafkaConfig
import ru.clevertec.config.getKafkaConfig
import ru.clevertec.dto.kafka.BookEvent
import ru.clevertec.kafka.handler.EventHandler
import service.email.EmailService
import service.email.SmtpEmailService

val kafkaModule = DI.Module("kafkaModule") {
    bind<KafkaConfig>() with singleton { instance<ApplicationEnvironment>().config.getKafkaConfig() }
    bind<Map<String, EventHandler<*>>>() with singleton {
        mapOf(
            instance<KafkaConfig>().topics.bookEvents to instance<EventHandler<BookEvent>>()
        )
    }
    bind<UserContactService>() with singleton { instance<UserContactService>() }
    bind<EmailService>() with singleton { instance<SmtpEmailService>() }
    bind<EventHandler<NotificationEvent>>() with singleton { NotificationEventHandler(instance(), instance()) }
    bind<KafkaConsumerService>() with singleton { KafkaConsumerServiceImpl(instance(), instance()) }
}