package com.dennisdevs.cocinApp.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.model.data.Recipe
import com.dennisdevs.cocinApp.navigation.Pantry
import com.dennisdevs.cocinApp.navigation.RecipeDetail
import com.dennisdevs.cocinApp.navigation.RecipeHome
import com.dennisdevs.cocinApp.navigation.UserProfile
import com.dennisdevs.cocinApp.ui.theme.Verde40
import com.dennisdevs.cocinApp.ui.theme.onPrimaryLight
import com.dennisdevs.cocinApp.ui.theme.onSecondaryLight
import com.dennisdevs.cocinApp.ui.theme.secondaryContainerLight
import com.dennisdevs.cocinApp.ui.theme.secondaryLight

@Composable
fun ButtonBack(navController: NavController) {
    Row(modifier = Modifier
        .padding(start = 35.dp, end = 35.dp, top = 45.dp, bottom = 30.dp)
        .clickable { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
            contentDescription = "ButtonBack",
            modifier = Modifier.size(28.dp),
            tint = onPrimaryLight,
        )
    }
}

@Composable
fun TopBarWithoutSearch(navController: NavController, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            modifier = Modifier.clickable {
                navController.popBackStack()
            }, imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "Back"
        )

        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)

        Image(painter = painterResource(id = R.drawable.user_image),
            contentDescription = "User",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .clickable {
                    navController.navigate(UserProfile) {
                        popUpTo(RecipeHome) { inclusive = false }
                    }
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(navController: NavController, recipes: List<Recipe>) {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val onActiveChange: (Boolean) -> Unit = { active = it }
    val colors1 = SearchBarDefaults.colors(
        containerColor = secondaryContainerLight, dividerColor = onSecondaryLight
    )

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                    active = false
                },
                expanded = active,
                onExpandedChange = onActiveChange,
                enabled = true,
                placeholder = { (Text(text = stringResource(id = R.string.findPerType))) },
                leadingIcon = {
                    if (active) {
                        IconButton(onClick = { onActiveChange(false) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = onSecondaryLight
                            )
                        }
                    } else {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "ExpandMenu",
                                tint = onSecondaryLight
                            )
                        }
                    }
                    MaterialTheme(
                        shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
                    ) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .shadow(10.dp)
                                .background(color = secondaryLight)
                                .padding(6.dp, 2.dp),
                            offset = DpOffset(25.dp, 8.dp)
                        ) {
                            MyDropDownMenuItem(imageVector = Icons.Outlined.Home,
                                text = stringResource(id = R.string.home),
                                onClick = { navController.navigate(RecipeHome) })
                            MyDropDownMenuItem(imageVector = Icons.Rounded.Kitchen,
                                text = stringResource(id = R.string.pantry),
                                onClick = { navController.navigate(Pantry) })
                            MyDropDownMenuItem(imageVector = Icons.Outlined.Favorite,
                                text = stringResource(id = R.string.favorite),
                                onClick = { navController.navigate(UserProfile) })
                            MyDropDownMenuItem(imageVector = Icons.Rounded.Person,
                                text = stringResource(id = R.string.perfil),
                                onClick = { navController.navigate(UserProfile) })
                        }
                    }
                },
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(start = 5.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }, modifier = Modifier) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Clear",
                                    tint = Verde40
                                )
                            }
                        }
                        Image(painter = painterResource(id = R.drawable.user_image),
                            contentDescription = "User",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterVertically)
                                .clickable { navController.navigate(UserProfile) }
                        )
                    }
                },
                colors = TextFieldDefaults.colors(focusedTextColor = Color.White),
                interactionSource = null,
            )
        },
        expanded = active,
        onExpandedChange = onActiveChange,
        modifier = Modifier,
        shape = SearchBarDefaults.inputFieldShape,
        colors = colors1,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
        content = {
            if (query.isNotEmpty()) {
                // Filtrar las recetas por 'kitchenType' que coincida con el texto ingresado
                val filteredRecipes = recipes.filter {
                    it.kitchenType.contains(query, ignoreCase = true)
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(2.dp),
                    modifier = Modifier.wrapContentSize()
                ) {
                    items(filteredRecipes) { recipe ->
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
        },
    )
}