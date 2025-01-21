package com.dennisdevs.cocinApp.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.model.data.Recipe
import com.dennisdevs.cocinApp.navigation.RecipeHome
import com.dennisdevs.cocinApp.ui.components.DifficultInfo
import com.dennisdevs.cocinApp.ui.components.IngredientsList
import com.dennisdevs.cocinApp.ui.components.KitchenTypeInfo
import com.dennisdevs.cocinApp.ui.components.MyColumnInfo
import com.dennisdevs.cocinApp.ui.components.MyRowInfo
import com.dennisdevs.cocinApp.ui.components.PreparationSteps
import com.dennisdevs.cocinApp.ui.components.RationsInfo
import com.dennisdevs.cocinApp.ui.components.RecipeImageBox
import com.dennisdevs.cocinApp.ui.components.TextDescription
import com.dennisdevs.cocinApp.ui.components.TimeInfo
import com.dennisdevs.cocinApp.ui.components.TitleText
import com.dennisdevs.cocinApp.ui.components.TitleTextSmall
import com.dennisdevs.cocinApp.ui.components.TopBarWithoutSearch
import com.dennisdevs.cocinApp.ui.theme.errorContainerLight
import com.dennisdevs.cocinApp.ui.theme.primaryContainerLight
import com.dennisdevs.cocinApp.ui.theme.surfaceVariantLight
import com.dennisdevs.cocinApp.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
) {
    val recipe = navController.previousBackStackEntry?.savedStateHandle?.get<Recipe>("recipe")

    if (recipe == null) {
        navController.popBackStack(RecipeHome, inclusive = false)
    } else {
        var canCookRecipe by remember { mutableStateOf(false) }

        LaunchedEffect(recipe) {
            recipeViewModel.isRecipeAvailableForCooking(recipe) { result ->
                canCookRecipe = result
            }
        }

        // Observar la lista de favoritos y recalcular el estado de 'isLiked'
        val favoriteRecipes = recipeViewModel.favorites
        val isLiked = favoriteRecipes.contains(recipe)

        Column(
            modifier
                .fillMaxSize()
                .background(surfaceVariantLight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarWithoutSearch(navController, stringResource(id = R.string.cocin_app))

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .background(shape = RoundedCornerShape(30f), color = primaryContainerLight),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    RecipeImageBox(imageUrl = recipe.image,
                        isLiked = isLiked,
                        onLikeClicked = { newLikeState ->
                            recipeViewModel.toggleFavorite(recipe, newLikeState)
                        })

                    TitleText(title = recipe.title)

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MyColumnInfo(
                                modifier = Modifier.weight(1f)
                            ) {
                                MyRowInfo(content = { TimeInfo(time = recipe.time) })
                                MyRowInfo(content = { DifficultInfo(difficult = recipe.difficulty) })
                            }

                            MyColumnInfo(
                                modifier = Modifier.weight(1f)
                            ) {
                                MyRowInfo(content = { RationsInfo(rations = recipe.rations) })
                                KitchenTypeInfo(kitchenType = recipe.kitchenType)
                            }
                        }
                    }

                    Spacer(modifier = modifier.height(15.dp))

                    TitleTextSmall(text = stringResource(id = R.string.description))

                    TextDescription(description = recipe.description)

                    Spacer(modifier = modifier.height(30.dp))

                    TitleTextSmall(text = stringResource(id = R.string.ingredients))

                    IngredientsList(ingredients = recipe.ingredients)

                    Spacer(modifier = modifier.height(20.dp))

                    TitleTextSmall(text = stringResource(id = R.string.prepartation))

                    PreparationSteps(preparation = recipe.preparation)

                    if (canCookRecipe) {
                        Button(
                            onClick = {
                                // Aquí puedes manejar lo que suceda cuando el usuario haga clic en el botón
                                // Ejemplo: Mostrar mensaje, ir a una nueva pantalla, etc.
                                Log.d("RecipeDetailScreen", "Receta lista para cocinar!")
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cook_recipe_button),
                                color = Color.White
                            )
                        }
                    } else {
                        Text(
                            text = stringResource(id = R.string.not_enough_ingredients),
                            color = Color.White,
                            modifier = Modifier
                                .padding(8.dp)
                                .background(errorContainerLight, shape = CircleShape)
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                        )
                    }
                }
            }
        }
    }
}