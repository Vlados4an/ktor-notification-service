package service.email

import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties

class SmtpEmailService(
    private val host: String,
    private val port: Int,
    private val username: String,
    private val password: String,
    private val fromEmail: String,
    private val useTls: Boolean = true
) : EmailService {

    override suspend fun send(to: String, subject: String, body: String) = withContext(Dispatchers.IO) {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", useTls.toString())
            put("mail.smtp.host", host)
            put("mail.smtp.port", port.toString())
        }

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        val msg = MimeMessage(session).apply {
            setFrom(fromEmail)
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
            setSubject(subject, "UTF-8")
            setText(body, "UTF-8")
        }

        Transport.send(msg)
    }
}