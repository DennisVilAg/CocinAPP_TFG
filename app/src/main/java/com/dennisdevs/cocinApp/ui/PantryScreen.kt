package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.ui.components.MyCardTypeOfIngredients
import com.dennisdevs.cocinApp.ui.components.Splash
import com.dennisdevs.cocinApp.ui.components.TopBarWithoutSearch
import com.dennisdevs.cocinApp.ui.theme.Verde20
import com.dennisdevs.cocinApp.ui.theme.Verde40
import com.dennisdevs.cocinApp.ui.theme.surfaceVariantLight
import com.dennisdevs.cocinApp.viewmodel.PantryViewModel

@Composable
fun PantryScreen(
    pantryViewModel: PantryViewModel = viewModel(), navController: NavController
) {
    val pantry by pantryViewModel.pantry.collectAsState()
    val isLoading = pantry.isEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(surfaceVariantLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarWithoutSearch(navController, stringResource(id = R.string.myPantry))

        //SearchIngredients()

        if (isLoading) {
            Splash()
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp)
            ) {
                itemsIndexed(pantry) { index, pantry ->
                    val backgroundColor = if (index % 2 == 0) Verde20 else Verde40

                    MyCardTypeOfIngredients(
                        pantry,
                        navController,
                        CardDefaults.cardColors(containerColor = backgroundColor)
                    )
                }
            }
        }
    }
}

/*
@Preview
@Composable
fun DefaultPreviewOfPantryScreen() {
    PantryScreen()
}
*/