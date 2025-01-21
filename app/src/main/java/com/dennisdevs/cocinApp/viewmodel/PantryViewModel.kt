package com.dennisdevs.cocinApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennisdevs.cocinApp.model.data.Pantry
import com.dennisdevs.cocinApp.model.repositories.PantryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PantryViewModel : ViewModel() {
    private val pantryRepository = PantryRepository()

    private val _pantry = MutableStateFlow<List<Pantry>>(emptyList())
    val pantry: MutableStateFlow<List<Pantry>> get() = _pantry

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _pantry.value = pantryRepository.getTypeOfIngredients()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar los ingredientes: ${e.localizedMessage}"
            }
        }
    }
}