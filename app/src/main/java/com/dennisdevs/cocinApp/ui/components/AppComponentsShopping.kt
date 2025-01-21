package com.dennisdevs.cocinApp.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.model.data.IngredientsForPantry
import com.dennisdevs.cocinApp.ui.theme.BackgroundButtonSignUp
import com.dennisdevs.cocinApp.ui.theme.Verde20
import com.dennisdevs.cocinApp.ui.theme.Verde40
import com.dennisdevs.cocinApp.ui.theme.VerdeClaro
import com.dennisdevs.cocinApp.ui.theme.errorContainerLight
import com.dennisdevs.cocinApp.ui.theme.onSurfaceVariantDark
import com.dennisdevs.cocinApp.ui.theme.onSurfaceVariantLight
import com.dennisdevs.cocinApp.ui.theme.outlineDark
import com.dennisdevs.cocinApp.ui.theme.primaryContainerLight
import com.dennisdevs.cocinApp.ui.theme.surfaceVariantLight

@Composable
fun IngredientItemEdit(
    ingredient: IngredientsForPantry, isSelected: Boolean, onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                if (isSelected) primaryContainerLight else BackgroundButtonSignUp,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ingredient.name,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            fontSize = 16.sp
        )
        Text(
            text = "${ingredient.quantity.toInt()} ${ingredient.unit} en desp.",
            fontSize = 14.sp,
            color = outlineDark
        )
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(checkedColor = onSurfaceVariantDark)
        )
    }
}

@Composable
fun UpdateIngredientDialog(
    ingredient: IngredientsForPantry, onDismiss: () -> Unit, onSave: (IngredientsForPantry) -> Unit
) {
    var quantity by remember { mutableStateOf(0) }
    val baseQuantity = ingredient.quantity.toInt()

    fun getStep(unit: String, quantity: Int): Int {
        return when (unit.lowercase()) {
            "g" -> 100
            "mg", "ml" -> if (quantity < 100) 10 else 100
            "kg", "ud" -> 1
            else -> 1
        }
    }

    val step = getStep(ingredient.unit, quantity)

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
                Text(
                    text = "Comprar: ${ingredient.name}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    letterSpacing = 1.sp,
                    color = onSurfaceVariantLight
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Tienes $baseQuantity ${ingredient.unit} de ${ingredient.name} en la despensa",
                    fontSize = 16.sp,
                    color = onSurfaceVariantLight,
                    modifier = Modifier.padding(bottom = 22.dp),
                    textAlign = TextAlign.Center
                )

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
                    // Botón "Menos"
                    IconButton(
                        onClick = {
                            if (quantity > 0) {
                                quantity = maxOf(0, quantity - step)
                            }
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, shape = CircleShape)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Disminuir",
                            tint = Verde40
                        )
                    }
                    // Texto de cantidad
                    Text(
                        text = "$quantity ${ingredient.unit}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Verde20,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // Botón "Más"
                    IconButton(
                        onClick = { quantity += step },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Verde40, shape = CircleShape)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Aumentar",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botones "Cancelar" y "Guardar"
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
                                text = stringResource(id = R.string.cancel),
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
                                elevation = 10.dp, //
                                shape = RoundedCornerShape(8.dp), clip = false
                            )
                    ) {
                        Button(
                            onClick = {
                                // Al guardar, sumamos la cantidad actual a la base y registramos la cantidad comprada
                                val updatedIngredient = ingredient.copy(
                                    quantity = (baseQuantity + quantity).toDouble(),
                                    purchasedQuantity = quantity.toDouble(),
                                    isSelectedPurchase = true
                                )
                                onSave(updatedIngredient)
                            },
                            colors = ButtonDefaults.buttonColors(onSurfaceVariantLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.buyIngredient),
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