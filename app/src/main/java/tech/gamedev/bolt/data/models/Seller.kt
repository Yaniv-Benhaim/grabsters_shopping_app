package tech.gamedev.bolt.data.models

class Seller(
    private val email: String,
    private val id: String,
    private val imgUrl: String,
    private val imgLogo: String,
    private val name: String

) {

    fun getImgLogo(): String {
        return imgLogo
    }

    fun getEmail(): String {
        return email
    }

    fun getId(): String {
        return id
    }

    fun getImgUrl(): String {
        return imgUrl
    }

    fun getName(): String {
        return name
    }
}