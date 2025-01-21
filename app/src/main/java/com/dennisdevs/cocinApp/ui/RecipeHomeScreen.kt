package com.dennisdevs.cocinApp.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.navigation.RecipeDetail
import com.dennisdevs.cocinApp.ui.components.MyCardRecipe
import com.dennisdevs.cocinApp.ui.components.MySearchBar
import com.dennisdevs.cocinApp.ui.components.ShoppingSplash
import com.dennisdevs.cocinApp.ui.components.Splash
import com.dennisdevs.cocinApp.ui.theme.onSurfaceVariantLight
import com.dennisdevs.cocinApp.ui.theme.surfaceVariantLight
import com.dennisdevs.cocinApp.ui.theme.tertiaryLight
import com.dennisdevs.cocinApp.viewmodel.RecipeViewModel

@Composable
fun RecipeHomeScreen(
    viewModel: RecipeViewModel = viewModel(), navController: NavController
) {
    val recipes by viewModel.allRecipes.collectAsState()//Lista de recetas
    val filteredRecipes by viewModel.filteredRecipes.collectAsState() // Lista filtrada
    val isFiltering by viewModel.isFiltering.collectAsState()
    val showOnlyFiltered by viewModel.showOnlyFiltered.collectAsState() // Obtener estado del filtro

    val withMyIngredientsText = stringResource(id = R.string.withPantry)
    val withoutMyIngredientsText = stringResource(id = R.string.withoutFilter)
    val withIngredients = stringResource(id = R.string.recipes)
    val recipesAvailable = stringResource(id = R.string.recipesAvailable)

    val titleText = if (showOnlyFiltered) recipesAvailable else withIngredients
    val buttonText = if (showOnlyFiltered) withoutMyIngredientsText else withMyIngredientsText

    val recipesToShow = if (showOnlyFiltered) filteredRecipes else recipes
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(surfaceVariantLight)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MySearchBar(navController, recipes = recipesToShow)

            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = titleText,
                textAlign = TextAlign.Center,
                color = onSurfaceVariantLight,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.padding(5.dp))

            when {
                isLoading || isFiltering -> {
                    Splash()
                }

                recipesToShow.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.noRecipesWithIngredientsInPantry),
                            textAlign = TextAlign.Center,
                            color = onSurfaceVariantLight,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        ShoppingSplash()
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(2.dp),
                        modifier = Modifier.wrapContentSize()
                    ) {
                        items(recipesToShow) { recipe ->
                            Log.d("RecipeHomeScreen", "Mostrando receta: ${recipe.title}")
                            MyCardRecipe(
                                modifier = Modifier.clickable {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "recipe", recipe
                                    )
                                    navController.navigate(RecipeDetail)
                                }, recipe = recipe, navController = navController
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                if (showOnlyFiltered) {
                    viewModel.resetRecipes()
                } else {
                    viewModel.filterRecipesWithPantry()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .shadow(3.dp, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = tertiaryLight)
        ) {
            Text(
                text = buttonText, fontSize = 14.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}