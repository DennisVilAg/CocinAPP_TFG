package com.dennisdevs.cocinApp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennisdevs.cocinApp.model.data.ShopList
import com.dennisdevs.cocinApp.model.repositories.HistoryShopListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryShopListViewModel  : ViewModel() {
    private val historyShopListRepository = HistoryShopListRepository()

    private val _historyShopLists = MutableStateFlow<List<ShopList>>(emptyList())
    val historyShopLists: StateFlow<List<ShopList>> = _historyShopLists

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadHistoryShopLists()
    }

    // Cargar historial de listas
    private fun loadHistoryShopLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lists = historyShopListRepository.getHistoryShopList()
                _historyShopLists.value = lists
            } catch (e: Exception) {
                handleError("Error al cargar el historial de listas", e)
            }
        }
    }

    private fun handleError(message: String, exception: Exception) {
        _errorMessage.value = "$message: ${exception.message}"
        Log.e("HistoryShopListViewModel", message, exception)
    }
}