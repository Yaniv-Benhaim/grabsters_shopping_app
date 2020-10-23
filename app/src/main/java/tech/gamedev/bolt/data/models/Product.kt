package tech.gamedev.bolt.data.models

class Product(
    private var name: String,
    private var description: String,
    private var price: Int,
    private var stock: Int,
    private var productImg: String
) {


    fun getName(): String{
        return name
    }

    fun getDescription(): String{
        return description
    }


    fun getPrice(): Int{
        return price
    }


    fun getStock(): Int{
        return stock
    }

    fun getProductImg(): String{
        return productImg
    }
}