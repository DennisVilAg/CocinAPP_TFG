package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.navigation.Login
import com.dennisdevs.cocinApp.navigation.Register
import com.dennisdevs.cocinApp.ui.components.BackgroundWelcomeScreenGradient
import com.dennisdevs.cocinApp.ui.components.LogoWelcome
import com.dennisdevs.cocinApp.ui.components.MyRedirectionButton
import com.dennisdevs.cocinApp.ui.theme.BackgroundButtonSignUp

@Composable
fun WelcomeScreen(/* */navController: NavHostController) {

    BackgroundWelcomeScreenGradient()

    //BackgroundWelcomeScreen()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(140.dp))

        LogoWelcome()

        Spacer(modifier = Modifier.height(300.dp))

        MyRedirectionButton(
            navController, Login, id = R.string.log_in
        )

        Spacer(modifier = Modifier.height(20.dp))

        MyRedirectionButton(
            navController, Register, ButtonDefaults.buttonColors(
                containerColor = BackgroundButtonSignUp, contentColor = Color.Black
            ), R.string.create_account
        )

        Spacer(modifier = Modifier.height(11.dp))

        //NotRegister(navController)
    }
}
