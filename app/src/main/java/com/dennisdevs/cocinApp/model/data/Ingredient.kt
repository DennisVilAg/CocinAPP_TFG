package com.dennisdevs.cocinApp.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val name: String,
    val quantity: Double,
    val unit: String,
    val category: String
) : Parcelable

//---HANDLING OF NULLS AND UNEXPECTED VALUES---

fun toIngredient(ingredientFromDb: Map<String, Any?>): Ingredient {
    val name = ingredientFromDb["name"] as? String ?: "Desconocido"
    val quantity =
        (ingredientFromDb["quantity"] as? Number)?.toDouble() ?: 0.0
    val unit = ingredientFromDb["unit"] as? String ?: "N/A"
    val category = ingredientFromDb["category"] as? String ?: "General"

    return Ingredient(name, quantity, unit, category)
}