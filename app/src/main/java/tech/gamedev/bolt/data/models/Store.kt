package tech.gamedev.bolt.data.models

class Store(
    private var name: String,
    private var description: String,
    private var imgBackground: String,
    private var imgLogo: String

) {

    fun getName(): String{
        return name
    }

    fun getDescription(): String{
        return description
    }

    fun getImgBackground(): String{
        return imgBackground
    }

    fun getImgLogo(): String{
        return imgLogo
    }
}