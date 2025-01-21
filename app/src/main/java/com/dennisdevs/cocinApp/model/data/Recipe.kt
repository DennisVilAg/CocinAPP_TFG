package com.dennisdevs.cocinApp.model.data

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val title: String,
    val image: String,
    val description: String,
    val kitchenType: String,
    val preparation: List<String>?,
    val ingredients: List<Ingredient>,
    val id: String,
    val time: String,
    val difficulty: String,
    val rations: Double,
    val isFavorite: Boolean = false
) : Parcelable


suspend fun toRecipe(recipeFromDb: Map<String, Any?>, documentId: String): Recipe {
    val ingredientsData = recipeFromDb["ingredients"] as? List<Map<String, Any?>> ?: emptyList()
    val ingredients: List<Ingredient> = ingredientsData.mapNotNull { ingredientData ->
        try {
            toIngredient(ingredientData)
        } catch (e: Exception) {
            Log.e("toRecipe", "Error procesando ingrediente: $ingredientData", e)
            null // Ignorar ingredientes mal formateados
        }
    }

    val imageField = recipeFromDb["image"]
    val imageUrl: String = when (imageField) {
        is String -> getImageUrl(imageField)
        is DocumentReference -> resolveImageFromDocument(imageField)
        else -> "default_image_url"
    }

    return Recipe(
        title = recipeFromDb["title"] as? String ?: "Sin título",
        image = imageUrl,
        description = recipeFromDb["description"] as? String ?: "Sin descripción",
        kitchenType = recipeFromDb["kitchenType"] as? String ?: "General",
        preparation = recipeFromDb["preparation"] as? List<String>,
        ingredients = ingredients,
        id = documentId,
        time = recipeFromDb["time"] as? String ?: "0 min",
        difficulty = recipeFromDb["difficulty"] as? String ?: "Fácil",
        rations = (recipeFromDb["rations"] as? Number)?.toDouble() ?: 1.0,
        isFavorite = recipeFromDb["isFavorite"] as? Boolean ?: false
    )
}

suspend fun getImageUrl(imageRef: String): String {
    val storageReference = Firebase.storage.getReferenceFromUrl(imageRef)
    return storageReference.downloadUrl.await().toString()
}

suspend fun resolveImageFromDocument(documentRef: DocumentReference): String {
    val documentSnapshot = documentRef.get().await()
    val imageUrl = documentSnapshot.getString("imageUrl")
    return imageUrl
        ?: throw IllegalArgumentException("DocumentReference no contiene un campo 'imageUrl'.")
}
