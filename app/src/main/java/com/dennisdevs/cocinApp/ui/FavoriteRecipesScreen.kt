package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.navigation.RecipeDetail
import com.dennisdevs.cocinApp.ui.components.MyCardRecipe
import com.dennisdevs.cocinApp.ui.theme.tertiaryLight
import com.dennisdevs.cocinApp.ui.components.SlideInColumn
import com.dennisdevs.cocinApp.viewmodel.RecipeViewModel

@Composable
fun FavoriteRecipesScreen(
    recipeViewModel: RecipeViewModel, navController: NavController
) {
    val favoriteRecipes = recipeViewModel.favorites

    SlideInColumn {
        Text(
            text = "Mis recetas favoritas",
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
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (favoriteRecipes.isEmpty()) {
                Text(text = "No tienes recetas favoritas.", color = Color.Gray)
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(2.dp),
                    modifier = Modifier.wrapContentSize()
                ) {
                    items(favoriteRecipes) { recipe ->
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
}
