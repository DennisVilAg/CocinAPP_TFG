package com.dennisdevs.cocinApp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.navigation.Pantry
import com.dennisdevs.cocinApp.navigation.RecipeHome
import com.dennisdevs.cocinApp.ui.theme.onTertiaryLight
import com.dennisdevs.cocinApp.ui.theme.tertiaryContainerLight
import com.dennisdevs.cocinApp.ui.theme.tertiaryLight

@Composable
fun SixButtons(
    navController: NavController,
    onFavoritesClick: () -> Unit,
    onPurchaseHistoryClick: () -> Unit,
    onShopListClick: () -> Unit,
    onEditShopClick: () -> Unit,
    selectedScreen: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(tertiaryLight)
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column() {
            IconButton(
                onClick = { navController.navigate(Pantry) },
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = tertiaryContainerLight, contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Rounded.Kitchen, contentDescription = "Despensa")
            }
            Text(
                text = "Despensa",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column {
            IconButton(
                onClick = { navController.navigate(RecipeHome) },
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = tertiaryContainerLight, contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Outlined.RestaurantMenu, contentDescription = "Book")
            }
            Text(
                text = "Recetas",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column {
            IconButton(
                onClick = { onFavoritesClick() },
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    if (selectedScreen == "Favorites") onTertiaryLight else tertiaryContainerLight,
                    contentColor = if (selectedScreen == "Favorites") tertiaryContainerLight else Color.White
                )
            ) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Mis favoritos")
            }
            Text(
                text = "Favoritos",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column {
            IconButton(
                onClick = { onShopListClick() },
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    if (selectedScreen == "ShopList") onTertiaryLight else tertiaryContainerLight,
                    contentColor = if (selectedScreen == "ShopList") tertiaryContainerLight else Color.White
                )
            ) {
                Icon(imageVector = Icons.Filled.Receipt, contentDescription = "Lista de compra")
            }
            Text(
                text = "Lista",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column {
            IconButton(
                onClick = { onEditShopClick() },
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    if (selectedScreen == "EditShopList") onTertiaryLight else tertiaryContainerLight,
                    contentColor = if (selectedScreen == "EditShopList") tertiaryContainerLight else Color.White
                )
            ) {
                Icon(imageVector = Icons.Rounded.Checklist, contentDescription = "Editar lista")
            }
            Text(
                text = "Crear",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column {
            IconButton(
                onClick = { onPurchaseHistoryClick() },
                modifier = Modifier.size(50.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    if (selectedScreen == "PurchaseHistory") onTertiaryLight else tertiaryContainerLight,
                    contentColor = if (selectedScreen == "PurchaseHistory") tertiaryContainerLight else Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = "Historial de compra"
                )
            }
            Text(
                text = "Historial",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

    }
}

@Composable
fun NameAndEmailProfile(name: String, surName: String, email: String) {
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Text(
            text = "$name $surName", fontWeight = FontWeight.SemiBold, color = Color.White
        )
        Text(
            text = "Correo: $email", fontSize = 14.sp, color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun ImageProfile() {
    Image(
        painter = painterResource(id = R.drawable.user_image),
        contentDescription = "Image User",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
    )
}

@Composable
fun SlideInColumn(
    content: @Composable () -> Unit
) {
    // Estado que controla la visibilidad
    val visible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible.value = true
    }

    // Animación de deslizamiento en el Column
    AnimatedVisibility(
        visible = visible.value, enter = slideInVertically(
            initialOffsetY = { -it },  // Empieza desde la parte superior
            animationSpec = tween(
                durationMillis = 500, easing = FastOutSlowInEasing
            )
        ) + fadeIn(animationSpec = tween(durationMillis = 100)), modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content() // Este es el contenido que pasas al composable
        }
    }
}