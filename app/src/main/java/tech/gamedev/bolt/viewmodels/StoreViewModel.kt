package tech.gamedev.bolt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoreViewModel : ViewModel() {
    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> = _selectedCategory

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }
}