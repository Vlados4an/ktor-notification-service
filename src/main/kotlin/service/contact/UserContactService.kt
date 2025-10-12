package service.contact

interface UserContactService {
    fun getEmail(userId: Int): String?
}