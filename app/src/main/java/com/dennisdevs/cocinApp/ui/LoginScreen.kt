package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.navigation.ForgotPassword
import com.dennisdevs.cocinApp.navigation.Register
import com.dennisdevs.cocinApp.ui.components.BackgroundLoginScreenGradient
import com.dennisdevs.cocinApp.ui.components.ButtonBack
import com.dennisdevs.cocinApp.ui.components.GoogleSignInButton
import com.dennisdevs.cocinApp.ui.components.LoginButton
import com.dennisdevs.cocinApp.ui.components.LoginLogo
import com.dennisdevs.cocinApp.ui.components.MyCustomTextFieldLogInEmail
import com.dennisdevs.cocinApp.ui.components.MyCustomTextFieldLogInPassword
import com.dennisdevs.cocinApp.ui.components.MyDivider
import com.dennisdevs.cocinApp.ui.components.MyRedirectionButtonUnderline
import com.dennisdevs.cocinApp.ui.components.WelcomeTextLogin
import com.dennisdevs.cocinApp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    onGoogleSignInClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {

    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


    Scaffold(snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { data -> Snackbar(snackbarData = data) }
        )
    }) { innerPadding ->

        BackgroundLoginScreenGradient()
        ButtonBack(navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 50.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginLogo()
            Spacer(modifier = Modifier.height(65.dp))
            WelcomeTextLogin()
            Spacer(modifier = Modifier.height(10.dp))

            MyCustomTextFieldLogInEmail(value = email,
                onValueChange = { if (it.length <= 30) email = it })

            Spacer(modifier = Modifier.height(35.dp))

            MyCustomTextFieldLogInPassword(
                value = password,
                onValueChange = { if (it.length <= 30) password = it },
                passwordVisible = passwordVisible,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            LoginButton(
                email = email,
                password = password,
                keyboardController = keyboardController,
                navController = navController,
                scope = scope,
                snackbarHostState = snackbarHostState,
                authViewModel = authViewModel
            )

            MyDivider()

            GoogleSignInButton(onClick = { onGoogleSignInClick() })

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxSize(1f)
            ) {
                MyRedirectionButtonUnderline(
                    navController, Register, R.string.register
                )
                MyRedirectionButtonUnderline(
                    navController, ForgotPassword, R.string.forgot_password
                )
            }
        }
    }
}