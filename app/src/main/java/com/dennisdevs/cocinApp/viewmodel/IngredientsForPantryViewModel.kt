package com.dennisdevs.cocinApp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennisdevs.cocinApp.model.data.IngredientsForPantry
import com.dennisdevs.cocinApp.model.repositories.IngredientsForPantryRepository
import com.dennisdevs.cocinApp.utils.Logger
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class IngredientsForPantryViewModel : ViewModel() {
    private val ingredientsForPantryRepository = IngredientsForPantryRepository()

    //StateFlows
    private val _ingredientsForPantry = MutableStateFlow<List<IngredientsForPantry>>(emptyList())
    val ingredientsForPantry: StateFlow<List<IngredientsForPantry>> get() = _ingredientsForPantry

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _selectedIngredients = MutableStateFlow<Set<String>>(emptySet())
    val selectedIngredients: StateFlow<Set<String>> get() = _selectedIngredients

    private val _shoppingList = MutableStateFlow<List<IngredientsForPantry>>(emptyList())
    val shoppingList: StateFlow<List<IngredientsForPantry>> = _shoppingList.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        loadIngredientsForPantry()
        loadShoppingList()
    }

    // --- FIREBASE ---
    private fun loadIngredientsForPantry() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _ingredientsForPantry.value = ingredientsForPantryRepository.getIngredientsForPantry()
            } catch (e: Exception) {
                handleError("Error cargando los ingredientes", e)
            }
        }
    }

    //----LOAD SHOPPING LIST----
    private fun loadShoppingList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _shoppingList.value = ingredientsForPantryRepository.getShopList()
            } catch (e: Exception) {
                handleError("Error cargando la lista de compras", e)
            }
        }
    }

    //-----REPOSITORY-----ADD SHOP LIST-----
    fun addToShoppingList() {
        val selectedItems = _ingredientsForPantry.value.filter {
            _selectedIngredients.value.contains(it.id)
        }
        _shoppingList.value = selectedItems
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ingredientsForPantryRepository.saveShoppingList(selectedItems)
            } catch (e: Exception) {
                handleError("Error guardando la lista de compras", e)
            }
        }
    }

    //---CLEAR SHOP LIST---
    fun clearShoppingList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ingredientsForPantryRepository.clearShoppingList()
                _shoppingList.value = emptyList()
            } catch (e: Exception) {
                handleError("Error al limpiar la lista de compras", e)
            }
        }
    }

    //---ARCHIVE LIST---
    fun archiveShoppingList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ingredientsForPantryRepository.archiveShoppingList()
                _shoppingList.value = emptyList()
            } catch (e: Exception) {
                handleError("Error al archivar la lista de compras", e)
            }
        }
    }


    //---RESTO DE FUNCIONES UTILES---
    private fun handleError(message: String, exception: Throwable) {
        _errorMessage.value = "$message: ${exception.message}"
        logError(message, exception)
    }

    private fun logError(message: String, exception: Throwable) {
        Logger.logError(message, exception)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleIngredientSelection(ingredientId: String) {
        _selectedIngredients.value = _selectedIngredients.value.toMutableSet().apply {
            if (contains(ingredientId)) remove(ingredientId) else add(ingredientId)
        }
    }

    fun updateIngredient(updatedIngredient: IngredientsForPantry) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Firebase.firestore.collection("ingredients").document(updatedIngredient.id)
                    .update("quantity", updatedIngredient.quantity).await()

                _ingredientsForPantry.value = _ingredientsForPantry.value.map {
                    if (it.id == updatedIngredient.id) it.copy(quantity = updatedIngredient.quantity)
                    else it
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error actualizando el ingrediente: ${e.message}"
                logError("Error al actualizar el ingrediente", e)
            }
        }
    }

    fun updateIngredientByName(name: String, newQuantity: Double, isSelectedPurchase: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ingredientsForPantryRepository.updateIngredientByName(name, newQuantity, isSelectedPurchase)
                _ingredientsForPantry.value = _ingredientsForPantry.value.map { ingredient ->
                    if (ingredient.name == name) {
                        ingredient.copy(quantity = newQuantity, isSelectedPurchase = isSelectedPurchase)
                    } else ingredient
                }
            } catch (e: Exception) {
                handleError("Error actualizando ingrediente: $name", e)
            }
        }
    }

    private fun canArchiveShoppingList(): Boolean {
        // Verifica si todos los ingredientes en la lista tienen isSelectedPurchase = true
        val allSelected = _shoppingList.value.all {
            Log.d(
                "PantryViewModel",
                "Ingrediente: ${it.name}, isSelectedPurchase: ${it.isSelectedPurchase}"
            )
            it.isSelectedPurchase
        }

        Log.d("PantryViewModel", "Lista completa de ingredientes: ${_shoppingList.value}")
        Log.d("PantryViewModel", "Todos los ingredientes seleccionados: $allSelected")

        return allSelected
    }
}