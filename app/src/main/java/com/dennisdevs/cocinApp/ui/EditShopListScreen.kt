package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.ui.components.IngredientItemEdit
import com.dennisdevs.cocinApp.ui.theme.primaryLight
import com.dennisdevs.cocinApp.ui.theme.tertiaryLight
import com.dennisdevs.cocinApp.ui.components.SlideInColumn
import com.dennisdevs.cocinApp.viewmodel.IngredientsForPantryViewModel

@Composable
fun EditShopScreen(
    navController: NavController, viewModel: IngredientsForPantryViewModel = viewModel()
) {
    val ingredients by viewModel.ingredientsForPantry.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedIngredientIds by viewModel.selectedIngredients.collectAsState()
    val filteredIngredients = if (searchQuery.length >= 2) {
        ingredients.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
    } else {
        emptyList()
    }
    val selectedIngredients = ingredients.filter { selectedIngredientIds.contains(it.id) }
    // Obtener el estado de la lista de la compra desde el ViewModel
    val shoppingList =
        viewModel.shoppingList.collectAsState()

    // Verificar si ya hay elementos en la lista
    val isListNotEmpty = shoppingList.value.isNotEmpty()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SlideInColumn {
        Text(
            text = "Hacer lista de compra",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = 12.dp, bottomEnd = 12.dp
                    )
                )
                .background(tertiaryLight)
                .padding(10.dp, 6.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = { Text(stringResource(id = R.string.findIngredients)) },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    viewModel.updateSearchQuery("")
                }),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = primaryLight, focusedLabelColor = primaryLight
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ingredientes no seleccionados
                items(filteredIngredients.filter { it.id !in selectedIngredientIds }) { ingredient ->
                    IngredientItemEdit(ingredient = ingredient, isSelected = false, onClick = {
                        viewModel.toggleIngredientSelection(ingredient.id)
                        viewModel.updateSearchQuery("")
                    })
                }

                // Mostrar los ingredientes seleccionados al final de la lista
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.selectedIngredient),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    selectedIngredients.sortedBy { it.name.lowercase() }
                        .forEach { selectedIngredient ->
                            IngredientItemEdit(ingredient = selectedIngredient,
                                isSelected = true,
                                onClick = { viewModel.toggleIngredientSelection(selectedIngredient.id) })
                        }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Botón para añadir ingredientes seleccionados a la lista de la compra
                    Button(
                        onClick = {
                            if (!isListNotEmpty) {
                                viewModel.addToShoppingList()
                                navController.navigate("UserProfile/ShopList")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        enabled = !isListNotEmpty,
                        colors = ButtonDefaults.buttonColors(containerColor = primaryLight)
                    ) {
                        Text(
                            text = if (isListNotEmpty) stringResource(id = R.string.eraseList) else stringResource(
                                id = R.string.addToList
                            )
                        )
                    }
                }
            }
        }
    }
}

/*
@Composable
fun ShopListScreen(viewModel: IngredientsForPantryViewModel) {
    val shoppingList by viewModel.shoppingList.collectAsState()

    Log.d("ShopListScreen", "Lista de la compra: $shoppingList")

    LazyColumn(modifier = Modifier.height(100.dp)) {
        items(shoppingList) { ingredient ->
            IngredientItem(ingredient = ingredient,
                isSelected = true, // Puedes marcar como seleccionado si lo prefieres
                onClick = {})
        }
    }
}*/