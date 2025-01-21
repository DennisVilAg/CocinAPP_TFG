package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.model.data.IngredientsForPantry
import com.dennisdevs.cocinApp.ui.theme.errorContainerLight
import com.dennisdevs.cocinApp.ui.theme.onPrimaryLight
import com.dennisdevs.cocinApp.ui.theme.outlineDark
import com.dennisdevs.cocinApp.ui.theme.primaryContainerLight
import com.dennisdevs.cocinApp.ui.theme.secondaryContainerLight
import com.dennisdevs.cocinApp.ui.theme.tertiaryLight
import com.dennisdevs.cocinApp.ui.components.SlideInColumn
import com.dennisdevs.cocinApp.ui.components.UpdateIngredientDialog
import com.dennisdevs.cocinApp.viewmodel.IngredientsForPantryViewModel

@Composable
fun ShopListScreen(navController: NavController, viewModel: IngredientsForPantryViewModel) {
    val shoppingList by viewModel.shoppingList.collectAsState()

    SlideInColumn {
        Text(
            text = stringResource(id = R.string.shopList),
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

        if (shoppingList.isEmpty()) {
            Text(
                text = stringResource(id = R.string.emptyList),
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(0.dp, 12.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate("UserProfile/EditShopList") },
                    colors = ButtonDefaults.buttonColors(containerColor = tertiaryLight)
                ) {
                    Text(text = "Hacer la lista")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.introduceQuantity),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(shoppingList) { ingredient ->
                        IngredientItemList(item = ingredient, onEdit = { updatedIngredient ->
                            viewModel.updateIngredientByName(
                                name = updatedIngredient.name,
                                newQuantity = updatedIngredient.quantity,
                                isSelectedPurchase = updatedIngredient.isSelectedPurchase
                            )
                        })
                    }
                }

                Row(modifier = Modifier.padding(bottom = 12.dp)) {
                    Button(
                        onClick = {
                            viewModel.clearShoppingList()
                        },
                        colors = ButtonDefaults.buttonColors(errorContainerLight),
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.clear_list),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.archiveShoppingList()
                        },
                        colors = ButtonDefaults.buttonColors(primaryContainerLight),
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.archivePurchase),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientItemList(
    item: IngredientsForPantry, onEdit: (IngredientsForPantry) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var ingredientState by remember { mutableStateOf(item) }

    if (showDialog && !ingredientState.isSelectedPurchase) {
        UpdateIngredientDialog(ingredient = item,
            onDismiss = { showDialog = false },
            onSave = { updatedIngredient ->
                showDialog = false
                ingredientState = updatedIngredient.copy(isSelectedPurchase = true)
                onEdit(updatedIngredient)
            })
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                color = if (ingredientState.isSelectedPurchase) primaryContainerLight else secondaryContainerLight,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(14.dp, 10.dp)
            .clickable {
                if (!ingredientState.isSelectedPurchase) {
                    showDialog = true
                }
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = onPrimaryLight
        )
        if (ingredientState.isSelectedPurchase) {
            Column {
                Text(
                    text = "Comprado: ${ingredientState.purchasedQuantity.toInt()} ${ingredientState.unit}",
                    fontSize = 14.sp,
                    color = outlineDark,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(end = 10.dp, bottom = 2.dp)
                )
                Text(
                    text = "Total desp: ${ingredientState.quantity.toInt()} ${ingredientState.unit}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        } else {
            Text(
                text = "Desp. ", fontSize = 13.sp, color = outlineDark
            )
            Text(
                text = "${ingredientState.quantity.toInt()} ${ingredientState.unit}",
                fontSize = 14.sp,
                color = outlineDark,
                modifier = Modifier.padding(end = 6.dp)
            )
        }
        if (ingredientState.isSelectedPurchase) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Comprado",
                tint = Color.White
            )
        }
    }
}