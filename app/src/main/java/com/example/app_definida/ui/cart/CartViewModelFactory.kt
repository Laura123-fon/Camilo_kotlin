package com.example.app_definida.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_definida.data.local.CartProductDao

class CartViewModelFactory(private val dao: CartProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
