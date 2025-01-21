package com.dennisdevs.cocinApp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.model.data.IngredientsForPantry
import com.dennisdevs.cocinApp.model.data.Pantry
import com.dennisdevs.cocinApp.ui.theme.Verde
import com.dennisdevs.cocinApp.ui.theme.Verde10
import com.dennisdevs.cocinApp.ui.theme.Verde20
import com.dennisdevs.cocinApp.ui.theme.Verde40
import com.dennisdevs.cocinApp.ui.theme.VerdeClaro
import com.dennisdevs.cocinApp.ui.theme.errorContainerLight
import com.dennisdevs.cocinApp.ui.theme.onSurfaceLight
import com.dennisdevs.cocinApp.ui.theme.onSurfaceVariantLight
import com.dennisdevs.cocinApp.ui.theme.outlineVariantLight
import com.dennisdevs.cocinApp.ui.theme.secondaryLight
import com.dennisdevs.cocinApp.ui.theme.surfaceVariantLight
import com.dennisdevs.cocinApp.viewmodel.IngredientsForPantryViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun MyCardTypeOfIngredients(
    pantry: Pantry, navController: NavController, colors: CardColors
) {
    val typeOfIngredients = pantry.pantryName
    var expanded by remember {
        mutableStateOf(false)
    }
    val selectedCategory by remember { mutableStateOf(mutableListOf<String>()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow
                )
            ), elevation = CardDefaults.cardElevation(2.dp), colors = colors
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(12.dp)
                    .size(60.dp),
                model = pantry.icon,
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Inside,
                clipToBounds = true
            )

            VerticalDivider(
                thickness = 2.dp,
                color = Color.White,
                modifier = Modifier
                    .height(40.dp)
                    .padding(horizontal = 4.dp)
            )

            Column(
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 12.dp)
            ) {
                typeOfIngredients.forEachIndexed { _, step ->
                    Text(
                        text = "$step",
                        lineHeight = 18.sp,
                        letterSpacing = 1.sp,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                    ) // Show each step numbered
                    step?.let {
                        if (!selectedCategory.contains(it)) {
                            selectedCategory.add(it)
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.padding(end = 10.dp)) {
                IconButton(
                    onClick = { expanded = !expanded },
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "More Info",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
        if (expanded) {
            IngredientsTagList(selectedCategory)
        }
    }
}

@Composable
fun IngredientsTagList(
    selectedCategory: List<String>,
    ingredientsViewModel: IngredientsForPantryViewModel = viewModel()
) {
    val ingredientsForPantry by ingredientsViewModel.ingredientsForPantry.collectAsState()

    // Filter by selected category
    val filteredIngredients = ingredientsForPantry.filter { it.category in selectedCategory }

    //Order the ingredients, placing the ones with quantity 0 at the end.
    val sortedIngredients = filteredIngredients.sortedWith { a, b ->
        when {
            a.quantity == 0.0 && b.quantity != 0.0 -> 1
            b.quantity == 0.0 && a.quantity != 0.0 -> -1
            else -> a.name.compareTo(b.name, ignoreCase = true) // Alphabetical order
        }
    }

    if (sortedIngredients.isEmpty()) {
        Text(
            text = stringResource(id = R.string.nothingHere),
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        )
    } else {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 8.dp, start = 6.dp, end = 5.dp),
            mainAxisSpacing = 6.dp,
            crossAxisSpacing = 8.dp
        ) {
            sortedIngredients.forEach { ingredient ->
                TagItem(
                    item = ingredient, onEdit = { updatedIngredient ->
                        ingredientsViewModel.updateIngredient(updatedIngredient)
                    }, isGrayedOut = ingredient.quantity == 0.0
                )
            }
        }
    }
}


@Composable
fun TagItem(
    item: IngredientsForPantry, onEdit: (IngredientsForPantry) -> Unit, isGrayedOut: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        EditIngredientDialog(ingredient = item, onDismiss = { showDialog = false }, onSave = {
            showDialog = false
            onEdit(it)
        })
    }

    Row(
        modifier = Modifier
            .shadow(elevation = 2.dp, shape = CircleShape)
            .background(
                color = if (isGrayedOut) secondaryLight else Verde,
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { showDialog = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            color = if (isGrayedOut) outlineVariantLight else Color.White,
            fontWeight = if (isGrayedOut) FontWeight.Normal else FontWeight.W500,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.width(3.dp))

        Row(
            modifier = Modifier
                .background(
                    color = if (isGrayedOut) onSurfaceLight else Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 7.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.quantity.toInt().toString(),
                fontWeight = if (isGrayedOut) FontWeight.Normal else FontWeight.W500,
                color = if (isGrayedOut) outlineVariantLight else Verde10,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(1.dp))
            Text(
                text = item.unit,
                fontWeight = if (isGrayedOut) FontWeight.Normal else FontWeight.W500,
                color = if (isGrayedOut) outlineVariantLight else Verde10,
                fontSize = 12.sp
            )
        }
    }
}


@Composable
fun EditIngredientDialog(
    ingredient: IngredientsForPantry, onDismiss: () -> Unit, onSave: (IngredientsForPantry) -> Unit
) {
    var quantity by remember { mutableStateOf(ingredient.quantity.toInt()) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(14.dp), color = surfaceVariantLight
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //---TITLE---
                Text(
                    text = "Editar: ${ingredient.name}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    letterSpacing = 1.sp,
                    color = onSurfaceVariantLight
                )

                Spacer(modifier = Modifier.height(16.dp))

                //---QUANTITY CONTROLS---
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp, shape = CircleShape, clip = false
                        )
                        .clip(shape = CircleShape)
                        .background(VerdeClaro)
                        .padding(12.dp, 8.dp)
                ) {
                    val step = when (ingredient.unit.lowercase()) {
                        "g" -> 100
                        "mg", "ml" -> if (quantity < 100) 10 else 100
                        "kg", "ud" -> 1
                        else -> 1
                    }

                    //---BUTTON DECREASE---
                    IconButton(
                        onClick = {
                            if (quantity > 0) quantity = maxOf(0, quantity - step)
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White, shape = CircleShape)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = Verde40
                        )
                    }

                    Text(
                        text = "$quantity ${ingredient.unit}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Verde20,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    //---BUTTON INCREASE---
                    IconButton(
                        onClick = { quantity += step },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Verde40, shape = CircleShape)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ---BUTTONS CANCEL AND SAVE---
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                            .shadow(
                                elevation = 10.dp, shape = RoundedCornerShape(8.dp), clip = false
                            )
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(errorContainerLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.close),
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(8.dp), clip = false
                            )
                    ) {
                        Button(
                            onClick = {
                                val updatedIngredient =
                                    ingredient.copy(quantity = quantity.toDouble())
                                onSave(updatedIngredient)
                            },
                            colors = ButtonDefaults.buttonColors(onSurfaceVariantLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.save),
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
