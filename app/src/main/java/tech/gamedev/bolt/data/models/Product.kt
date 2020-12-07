package tech.gamedev.bolt.data.models

class Product(
    private var name: String = "",
    private var description: String = "",
    private var price: Double = 0.0,
    private var stock: Long = 0,
    private var productImg: String = "",
    private var productId: String = "",
    private var model: String = "",
    private var specs: String = "",
    private var seller: String = "",
    private var category: String = "",
    private var isActive: Boolean = true,
    private var itemsInCart: Long = 1
) {

    fun getItemsInCart(): Long {
        return itemsInCart
    }

    fun setItemsInCart() {
        ++itemsInCart
    }

    fun getIsActive(): Boolean {
        return isActive
    }

    fun getCategory(): String {
        return category
    }

    fun getSeller(): String {
        return seller
    }

    fun getProductId(): String {
        return productId
    }

    fun getModel(): String {
        return model
    }

    fun getSpecs(): String {
        return specs
    }

    fun getName(): String {
        return name
    }

    fun getDescription(): String {
        return description
    }


    fun getPrice(): Double {
        return price
    }


    fun getStock(): Long {
        return stock
    }

    fun setStock(stock: Long) {
        this.stock += stock
    }

    fun getProductImg(): String {
        return productImg
    }
}