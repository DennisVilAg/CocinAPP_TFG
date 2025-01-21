package com.dennisdevs.cocinApp.model.repositories

import android.util.Log
import com.dennisdevs.cocinApp.model.data.ShopList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HistoryShopListRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getHistoryShopList(): List<ShopList> {
        return try {
            val querySnapshot = firestore.collection("shoppingListHistory")
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                document.toObject(ShopList::class.java)
            }
        } catch (e: Exception) {
            logError("Error al obtener el historial de listas", e)
            emptyList()
        }
    }

    private fun logError(message: String, exception: Exception) {
        Log.e("HistoryShopListRepository", "$message: ${exception.message}")
    }
}