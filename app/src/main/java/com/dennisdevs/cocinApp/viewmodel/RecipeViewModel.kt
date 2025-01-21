package com.dennisdevs.cocinApp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennisdevs.cocinApp.model.data.Ingredient
import com.dennisdevs.cocinApp.model.data.IngredientsForPantry
import com.dennisdevs.cocinApp.model.data.Recipe
import com.dennisdevs.cocinApp.model.repositories.IngredientsForPantryRepository
import com.dennisdevs.cocinApp.model.repositories.RecipesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipesRepository: RecipesRepository = RecipesRepository(),
    private val ingredientsForPantryRepository: IngredientsForPantryRepository = IngredientsForPantryRepository()
) : ViewModel() {

    private val _allRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val allRecipes: StateFlow<List<Recipe>> = _allRecipes.asStateFlow()

    private val _favorites = mutableStateListOf<Recipe>()
    val favorites: List<Recipe> get() = _favorites

    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes

    private val _isFiltering = MutableStateFlow(false)
    val isFiltering: StateFlow<Boolean> = _isFiltering

    private val _showOnlyFiltered = MutableStateFlow(false)
    val showOnlyFiltered: StateFlow<Boolean> = _showOnlyFiltered

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        if (_allRecipes.value.isEmpty()) {
            loadAllRecipes()
        }
    }

    //-----LOAD ALL RECIPES-----
    private fun loadAllRecipes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val recipes = recipesRepository.getRecipes()
                _allRecipes.value = recipes
                _favorites.clear()
                _favorites.addAll(recipes.filter { it.isFavorite })
            } catch (e: Exception) {
                _error.value = "Error al cargar las recetas: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    //-----FILTER RECIPES WITH PANTRY-----
    fun filterRecipesWithPantry() {
        viewModelScope.launch {
            _isFiltering.value = true
            try {
                val pantryIngredients = ingredientsForPantryRepository.getIngredientsForPantry()
                val filtered = _allRecipes.value.filter { recipe ->
                    areAllIngredientsInPantry(recipe.ingredients, pantryIngredients)
                }
                _filteredRecipes.value = filtered
                _showOnlyFiltered.value = true
            } catch (e: Exception) {
                _error.value = "Error al filtrar las recetas: ${e.localizedMessage}"
            } finally {
                _isFiltering.value = false
            }
        }
    }

    //-----FILTER INGREDIENTS IN PANTRY AND RECIPES-----
    private fun areAllIngredientsInPantry(
        recipeIngredients: List<Ingredient>,
        pantryIngredients: List<IngredientsForPantry>
    ): Boolean {
        return recipeIngredients.all { recipeIngredient ->
            pantryIngredients.any { pantryIngredient ->
                recipeIngredient.name.equals(pantryIngredient.name, ignoreCase = true) &&
                        pantryIngredient.quantity >= recipeIngredient.quantity
            }
        }
    }

    //-----TOGGLE FAVORITE-----
    fun toggleFavorite(recipe: Recipe, isLiked: Boolean) {
        if (isLiked) {
            if (!_favorites.contains(recipe)) {
                _favorites.add(recipe)
                updateFavoriteStatus(recipe, true)
            }
        } else {
            _favorites.remove(recipe)
            updateFavoriteStatus(recipe, false)
        }
    }

    //-----UPDATE FAVORITE STATUS-----
    private fun updateFavoriteStatus(recipe: Recipe, isLiked: Boolean) {
        viewModelScope.launch {
            try {
                recipesRepository.updateFavoriteStatus(recipe.id, isLiked)
            } catch (e: Exception) {
                _error.value = "Error al actualizar el estado de favorito: ${e.localizedMessage}"
            }
        }
    }

    //-----RESET RECIPES-----
    fun resetRecipes() {
        _filteredRecipes.value = emptyList()
        _showOnlyFiltered.value = false
    }

    //-----IS RECIPE AVAILABLE FOR COOKING-----
    fun isRecipeAvailableForCooking(recipe: Recipe, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val pantryIngredients = ingredientsForPantryRepository.getIngredientsForPantry()
            val result = areAllIngredientsInPantry(recipe.ingredients, pantryIngredients)
            onResult(result)
        }
    }

    //-----GET FAVORITES-----
    fun getFavorites() {
        _favorites.clear()
        _allRecipes.value.filter { it.isFavorite }.forEach {
            _favorites.add(it)
        }
    }
}