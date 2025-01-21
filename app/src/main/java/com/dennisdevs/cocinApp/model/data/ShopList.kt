package com.dennisdevs.cocinApp.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopList(
    val id: String = "",
    val name: String = "",
    val items: List<IngredientsForPantry> = emptyList(),
    val timestamp: Long = 0L
) : Parcelable
