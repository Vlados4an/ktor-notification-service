package service.email

interface EmailService {
    suspend fun send(to: String, subject: String, body: String)
}