package com.dennisdevs.cocinApp.model.repositories

import android.util.Log
import com.dennisdevs.cocinApp.model.data.IngredientsForPantry
import com.dennisdevs.cocinApp.model.data.toIngredientsForPantry
import com.dennisdevs.cocinApp.utils.Logger.logError
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class IngredientsForPantryRepository {

    /*--------------------------------------
    --------------INGREDIENTS---------------
    ---------------------------------------*/

    //-----FIND INGREDIENT BY NAME-----
    private suspend fun findIngredientDocumentByName(name: String): DocumentSnapshot? {
        return try {
            val querySnapshot = Firebase.firestore
                .collection("ingredients")
                .whereEqualTo("name", name)
                .get()
                .await()

            querySnapshot.documents.firstOrNull()
        } catch (e: Exception) {
            logError("Error buscando ingrediente con nombre: $name", e)
            null
        }
    }

    //-----UPDATE INGREDIENT-----
    private suspend fun updateIngredientDocument(documentId: String, newQuantity: Double) {
        try {
            Firebase.firestore.collection("ingredients")
                .document(documentId)
                .update("quantity", newQuantity)
                .await()
        } catch (e: Exception) {
            logError("Error actualizando el documento del ingrediente con ID: $documentId", e)
        }
    }

    //-----GET ALL INGREDIENTS FOR PANTRY-----
    suspend fun getIngredientsForPantry(): List<IngredientsForPantry> {
        return try {
            val querySnapshot = Firebase.firestore.collection("ingredients").get().await()
            querySnapshot.documents.mapNotNull { document ->
                document.data?.let { data ->
                    toIngredientsForPantry(document.id, data)
                }
            }
        } catch (e: Exception) {
            logError("Error obteniendo ingredientes de la despensa desde Firestore", e)
            emptyList()
        }
    }

    //-----UPDATE INGREDIENT BY NAME-----
    suspend fun updateIngredientByName(name: String, newQuantity: Double, isSelectedPurchase: Boolean) {
        try {
            // Buscar el documento del ingrediente por nombre
            val document = findIngredientDocumentByName(name)
            if (document != null) {
                // Actualizar el documento en Firebase
                updateIngredientDocument(
                    documentId = document.id,
                    newQuantity = newQuantity
                )

                // Log correcto
                Log.d(
                    "IngredientsForPantryRepository",
                    "Ingrediente actualizado correctamente: $name, cantidad: $newQuantity, seleccionado: $isSelectedPurchase"
                )
            } else {
                Log.w("IngredientsForPantryRepository", "Ingrediente no encontrado: $name")
            }
        } catch (e: Exception) {
            logError("Error actualizando ingrediente: $name", e)
        }
    }


    /*--------------------------------------
    --------------SHOP LIST---------------
    ---------------------------------------*/

    //-----GET SHOP LIST-----
    suspend fun getShopList(): List<IngredientsForPantry> {
        return try {
            val querySnapshot = Firebase.firestore.collection("shoppingLists").get().await()
            querySnapshot.documents.flatMap { doc ->
                (doc["items"] as? List<Map<String, Any?>>)?.mapNotNull { item ->
                    runCatching { toIngredientsForPantry(doc.id, item) }.getOrNull()
                } ?: emptyList()
            }
        } catch (e: Exception) {
            logError("Error obteniendo la lista de compras de Firestore", e)
            emptyList()
        }
    }

    //-----SAVE LIST-----
    suspend fun saveShoppingList(selectedItems: List<IngredientsForPantry>) {
        try {
            Firebase.firestore.collection("shoppingLists").add(mapOf("items" to selectedItems)).await()
        } catch (e: Exception) {
            throw Exception("Error al guardar la lista de compras en Firebase", e)
        }
    }

    //------CLEAR SHOP LIST-----
    suspend fun clearShoppingList() {
        try {
            val shoppingListCollection = Firebase.firestore.collection("shoppingLists")
            shoppingListCollection.get().await().documents.forEach { doc ->
                shoppingListCollection.document(doc.id).delete().await()
            }
        } catch (e: Exception) {
            logError("Error al limpiar la lista de compras", e)
        }
    }

    //-----ARCHIVE LIST-----
    suspend fun archiveShoppingList() {
        try {
            val shoppingListCollection = Firebase.firestore.collection("shoppingLists")
            val historyCollection = Firebase.firestore.collection("shoppingListHistory")

            val documents = shoppingListCollection.get().await().documents
            documents.forEach { doc ->
                val data = doc.data ?: return@forEach
                val timestamp = System.currentTimeMillis()

                val dataWithTimestamp = data.toMutableMap()
                dataWithTimestamp["timestamp"] = timestamp

                historyCollection.add(dataWithTimestamp).await()

                shoppingListCollection.document(doc.id).delete().await()
            }
        } catch (e: Exception) {
            logError("Error al archivar la lista de compras", e)
        }
    }
}