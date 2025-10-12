package ru.clevertec.modules

import dto.notification.NotificationEvent
import io.ktor.server.application.*
import kafka.consumer.KafkaConsumerService
import kafka.consumer.KafkaConsumerServiceImpl
import kafka.handler.NotificationEventHandler
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import ru.clevertec.config.KafkaConfig
import ru.clevertec.config.getKafkaConfig
import ru.clevertec.kafka.handler.EventHandler
import ru.clevertec.service.contact.UserContactServiceImpl
import service.contact.UserContactService
import service.email.EmailService
import service.email.SmtpEmailService

val kafkaModule = DI.Module("kafkaModule") {
    bind<KafkaConfig>() with singleton { instance<ApplicationEnvironment>().config.getKafkaConfig() }
    bind<UserContactService>() with singleton { UserContactServiceImpl() }
    bind<Map<String, EventHandler<*>>>() with singleton {
        mapOf(
            instance<KafkaConfig>().topics.notificationEvents to instance<EventHandler<NotificationEvent>>()
        )
    }
    bind<EmailService>() with singleton {
        val config = instance<ApplicationEnvironment>().config
        SmtpEmailService(
            host = config.property("smtp.host").getString(),
            port = config.property("smtp.port").getString().toInt(),
            username = config.property("smtp.username").getString(),
            password = config.property("smtp.password").getString(),
            fromEmail = config.property("smtp.fromEmail").getString(),
            useTls = config.property("smtp.useTls").getString().toBoolean()
        )
    }
    bind<EventHandler<NotificationEvent>>() with singleton { NotificationEventHandler(instance(), instance()) }
    bind<KafkaConsumerService>() with singleton { KafkaConsumerServiceImpl(instance(), instance()) }
}