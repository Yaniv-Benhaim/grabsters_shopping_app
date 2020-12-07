package tech.gamedev.bolt.viewmodels

import androidx.lifecycle.ViewModel
import tech.gamedev.bolt.data.repositories.FirebaseRepo

class MainViewModel : ViewModel() {

    private val firebaseRepo: FirebaseRepo = FirebaseRepo()


    fun getUser() = firebaseRepo.getUser()

    fun createUser() = firebaseRepo.createUser()


    fun getAllStores() = firebaseRepo.getStoresList()

    /*fun getProductsLiveData() = firebaseRepo.getProductList()*/
}