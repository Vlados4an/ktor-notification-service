package service.contact

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.LoggerFactory
import java.lang.Exception

class UserContactServiceImpl(
    private val httpClient: HttpClient,
    private val userServiceUrl: String
) : UserContactService {

    private val logger = LoggerFactory.getLogger(UserContactServiceImpl::class.java)

    override suspend fun getEmail(userId: Int): String? {
        return try {
            val response = httpClient.get("$userServiceUrl/api/v1/users/$userId/email") {
                accept(ContentType.Application.Json)
            }
            
            if (response.status.isSuccess()) {
                response.body<String?>()?.takeIf { it.isNotBlank() }
            } else {
                logger.warn("Failed to fetch email for user $userId: ${response.status}")
                null
            }
        } catch (e: Exception) {
            logger.error("Error fetching email for user $userId", e)
            null
        }
    }
}