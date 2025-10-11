package ru.clevertec.modules

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import repository.NotificationRepository
import repository.NotificationRepositoryImpl
import service.NotificationService
import service.NotificationServiceImpl

val notificationModule = DI.Module("notificationModule") {
    bind<NotificationRepository>() with singleton { NotificationRepositoryImpl() }
    bind<NotificationService>() with singleton { NotificationServiceImpl(instance()) }
}