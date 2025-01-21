package com.dennisdevs.cocinApp.model.repositories

import com.dennisdevs.cocinApp.model.data.Ingredient
import com.dennisdevs.cocinApp.model.data.Recipe
import com.dennisdevs.cocinApp.model.data.toRecipe
import com.dennisdevs.cocinApp.utils.Logger.logError
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RecipesRepository {

    //-----GET RECIPES-----
    suspend fun getRecipes(): List<Recipe> {
        return try {
            val querySnapshot = Firebase.firestore.collection("recipes").get().await()
            querySnapshot.documents.mapNotNull { document ->
                document.data?.let { data ->
                    toRecipe(data, document.id)
                }
            }
        } catch (e: Exception) {
            logError("Error obteniendo recetas desde Firestore", e)
            emptyList()
        }
    }

    //-----GET INGREDIENTS-----
    suspend fun getIngredients(): List<Ingredient> {
        return try {
            val querySnapshot = Firebase.firestore.collection("recipes").get().await()
            querySnapshot.documents.flatMap { document ->
                document.data?.let { data ->
                    val recipe = toRecipe(data, document.id)
                    recipe.ingredients
                } ?: emptyList()
            }
        } catch (e: Exception) {
            logError("Error obteniendo ingredientes de recetas desde Firestore", e)
            emptyList()
        }
    }

    //-----UPDATE FAVORITE STATUS-----
    suspend fun updateFavoriteStatus(recipeId: String, isLiked: Boolean) {
        try {
            val recipeRef = Firebase.firestore.collection("recipes").document(recipeId)
            recipeRef.update("isFavorite", isLiked).await()
        } catch (e: Exception) {
            throw Exception("Error al actualizar el estado de favorito", e)
        }
    }
}