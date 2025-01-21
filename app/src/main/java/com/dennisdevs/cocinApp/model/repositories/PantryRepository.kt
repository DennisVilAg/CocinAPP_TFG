package com.dennisdevs.cocinApp.model.repositories

import android.util.Log
import com.dennisdevs.cocinApp.model.data.Pantry
import com.dennisdevs.cocinApp.model.data.toPantry
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class PantryRepository {
    suspend fun getTypeOfIngredients(): List<Pantry> {
        return try {
            val snapshot = Firebase.firestore.collection("despensa").get().await()
            val result = mutableListOf<Pantry>()
            for (document in snapshot.documents) {
                document.data?.let {
                    result.add(toPantry(it))
                }
            }
            result
        } catch (e: Exception) {
            Log.e("PantryRepository", "Error fetching ingredients", e)
            emptyList()
        }
    }
}