package com.dennisdevs.cocinApp.model.data

import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pantry(
    val pantryName: List<String?>,
    val icon: String
) : Parcelable

suspend fun toPantry(pantryFromDb: MutableMap<String, Any>): Pantry {
    val names = pantryFromDb["pantryName"] as? List<String?> ?: emptyList()

    val imageField = pantryFromDb["icon"]
    val iconUrl: String = when (imageField) {
        //---IF IT IS A STRING, WE TREAT IT AS A STORAGE URL---
        is String -> getImageUrl(imageField)
        //---IF IT´S A DocumentReference, GET THE URL FROM THERE
        is DocumentReference -> resolveImageFromDocument(imageField)
        else -> throw IllegalArgumentException("El campo 'image' tiene un tipo no soportado.")
    }
    return Pantry(names, iconUrl)
}
