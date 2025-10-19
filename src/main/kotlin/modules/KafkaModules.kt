package ru.clevertec.modules

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
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
import ru.clevertec.dto.dto.NotificationEvent
import ru.clevertec.kafka.handler.EventHandler
import service.contact.UserContactService
import service.contact.UserContactServiceImpl
import service.email.EmailService
import service.email.SmtpEmailService

val kafkaModule = DI.Module("kafkaModule") {
    bind<KafkaConfig>() with singleton { instance<ApplicationEnvironment>().config.getKafkaConfig() }

    bind<HttpClient>() with singleton {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    bind<UserContactService>() with singleton {
        UserContactServiceImpl(
            httpClient = instance(),
            userServiceUrl = "http://localhost:8091"
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

    bind<EventHandler<NotificationEvent>>() with singleton {
        NotificationEventHandler(instance(), instance())
    }

    bind<Map<String, EventHandler<*>>>() with singleton {
        mapOf(
            instance<KafkaConfig>().topics.notificationEvents to instance<EventHandler<NotificationEvent>>()
        )
    }

    bind<KafkaConsumerService>() with singleton {
        KafkaConsumerServiceImpl(instance(), instance())
    }
}