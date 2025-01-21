package com.dennisdevs.cocinApp.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientsForPantry(
    val id: String = "",
    val category: String,
    val name: String,
    val quantity: Double,
    val unit: String,
    val isSelectedPurchase: Boolean,
    val purchasedQuantity: Double
) : Parcelable {
    constructor() : this("", "General", "Desconocido", 0.0, "N/A", false, 0.0)
}

fun toIngredientsForPantry(
    id: String,
    ingredientsForPantryFromDb: Map<String, Any?>
): IngredientsForPantry {
    val category = ingredientsForPantryFromDb["category"] as? String ?: "General"
    val name = ingredientsForPantryFromDb["name"] as? String ?: "Desconocido"
    val quantity = (ingredientsForPantryFromDb["quantity"] as? Number)?.toDouble() ?: 0.0
    val unit = ingredientsForPantryFromDb["unit"] as? String ?: "N/A"
    val isSelectedPurchase = ingredientsForPantryFromDb["isSelectedPurchase"] as? Boolean ?: false
    val purchasedQuantity =
        (ingredientsForPantryFromDb["purchasedQuantity"] as? Number)?.toDouble() ?: 0.0


    return IngredientsForPantry(
        id,
        category,
        name,
        quantity,
        unit,
        isSelectedPurchase,
        purchasedQuantity
    )
}
