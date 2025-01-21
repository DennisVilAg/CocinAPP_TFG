package com.dennisdevs.cocinApp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SignalCellularAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dennisdevs.cocinApp.model.data.Recipe
import com.dennisdevs.cocinApp.ui.theme.onPrimaryLight
import com.dennisdevs.cocinApp.ui.theme.primaryContainerLight

@Composable
fun MyDropDownMenuItem(
    imageVector: ImageVector, text: String, onClick: () -> Unit
) {
    DropdownMenuItem(
        leadingIcon = {
            Icon(
                imageVector = imageVector, contentDescription = ""
            )
        },
        text = { Text(text, fontSize = 16.sp) },
        onClick = onClick,
        colors = MenuDefaults.itemColors(
            textColor = Color.White, leadingIconColor = Color.White
        )
    )
}

@Composable
fun MyCardRecipe(modifier: Modifier, recipe: Recipe, navController: NavController) {
    Card(
        modifier
            .size(230.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = primaryContainerLight)
    ) {
        Box(
            modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    model = recipe.image,
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    clipToBounds = true
                )
                Text(
                    text = recipe.title,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    maxLines = 2,
                    lineHeight = 18.sp,
                    color = onPrimaryLight,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .padding(horizontal = 4.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = "time",
                            tint = onPrimaryLight
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = recipe.time, fontSize = 14.sp, color = onPrimaryLight
                        )
                        Text(
                            text = " | ",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 1.dp),
                            color = onPrimaryLight
                        )
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Outlined.SignalCellularAlt,
                            contentDescription = "difficult",
                            tint = onPrimaryLight
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = recipe.difficulty, fontSize = 14.sp, color = onPrimaryLight
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = recipe.kitchenType, fontSize = 14.sp, color = onPrimaryLight
                        )
                        Text(
                            text = " | ", fontSize = 18.sp, color = onPrimaryLight
                        )
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Outlined.Restaurant,
                            contentDescription = "",
                            tint = onPrimaryLight
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = recipe.rations.toInt().toString(),
                            fontSize = 14.sp,
                            color = onPrimaryLight
                        )
                    }
                }
            }
        }
    }
}