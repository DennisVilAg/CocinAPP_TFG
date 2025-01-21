package com.dennisdevs.cocinApp.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.model.data_sources.SessionManager
import com.dennisdevs.cocinApp.navigation.Login
import com.dennisdevs.cocinApp.navigation.RecipeHome
import com.dennisdevs.cocinApp.ui.components.ImageProfile
import com.dennisdevs.cocinApp.ui.components.NameAndEmailProfile
import com.dennisdevs.cocinApp.ui.components.SixButtons
import com.dennisdevs.cocinApp.ui.theme.errorContainerLight
import com.dennisdevs.cocinApp.ui.theme.tertiaryLight
import com.dennisdevs.cocinApp.viewmodel.AuthViewModel
import com.dennisdevs.cocinApp.viewmodel.RecipeViewModel

@Composable
fun UserProfileScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel,
    initialScreen: String = "Favorites",
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    var selectedScreen by remember { mutableStateOf(initialScreen) }

    var name by remember { mutableStateOf("") }
    var surName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }


    LaunchedEffect(key1 = true) {
        authViewModel.getUserData(onSuccess = { userName, userSurName, userEmail ->
            name = userName
            surName = userSurName
            email = userEmail
        }, onError = { errorMessage ->
            Log.e("UserProfile", "Error: $errorMessage")
        })
    }

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .background(tertiaryLight)
                .fillMaxWidth(1f)
                .fillMaxHeight(0.18f)
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            ImageProfile()

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom
            ) {
                NameAndEmailProfile(name = name, surName = surName, email = email)

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        authViewModel.signOut()
                        SessionManager.clearSession(context)
                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                        navController.navigate(Login) {
                            // Borrar todas las pantallas previas para que no pueda volver a ellas al presionar atrás
                            popUpTo(RecipeHome) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(36.dp)
                        .padding(start = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = errorContainerLight, // Color de fondo del botón
                        contentColor = Color.White// Color del texto
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.closeSession), fontSize = 15.sp
                    )
                }
            }
        }

        SixButtons(
            navController,
            onFavoritesClick = { selectedScreen = "Favorites" },
            onPurchaseHistoryClick = { selectedScreen = "PurchaseHistory" },
            onShopListClick = { selectedScreen = "ShopList" },
            onEditShopClick = { selectedScreen = "EditShopList" },
            selectedScreen = selectedScreen
        )

        when (selectedScreen) {
            "Favorites" -> FavoriteRecipesScreen(
                recipeViewModel = recipeViewModel, navController = navController
            )

            "PurchaseHistory" -> PurchaseHistoryScreen(viewModel())

            "ShopList" -> ShopListScreen(navController, viewModel())

            "EditShopList" -> EditShopScreen(navController = navController)
        }
    }
}