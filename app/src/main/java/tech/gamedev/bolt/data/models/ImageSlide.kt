package tech.gamedev.bolt.data.models

class ImageSlide(
    private var imageUrl: String,
    private var title: String,
    private var description: String
) {

    fun getImageUrl(): String {
        return imageUrl
    }

    fun getTitle(): String {
        return title
    }

    fun getDescription(): String {
        return description
    }
}