package service.contact

interface UserContactService {

    suspend fun getEmail(userId: Int): String?
}