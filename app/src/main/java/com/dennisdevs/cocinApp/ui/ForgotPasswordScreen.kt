package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.navigation.Login
import com.dennisdevs.cocinApp.ui.components.BackgroundLoginScreenGradient
import com.dennisdevs.cocinApp.ui.components.ButtonBack
import com.dennisdevs.cocinApp.ui.theme.BackgroundButtonOpenSession
import com.dennisdevs.cocinApp.ui.theme.BackgroundButtonSignUp
import com.dennisdevs.cocinApp.ui.theme.Verde40
import com.dennisdevs.cocinApp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var isSnackbarDismissed by remember { mutableStateOf(false) }

    LaunchedEffect(authViewModel.snackbarMessage.collectAsState().value) {
        authViewModel.snackbarMessage.collect { snackbarMessage ->
            snackbarMessage?.let {
                val snackbarResult = snackbarHostState.showSnackbar(
                    message = it,
                    duration = SnackbarDuration.Short
                )
                if (snackbarResult == SnackbarResult.Dismissed) {
                    authViewModel.clearSnackbarMessage()
                    if (it == "Si esta registrado se enviará el restablecimiento de la contraseña.") {
                        isSnackbarDismissed = true
                    }
                }
            }
        }
    }
    LaunchedEffect(isSnackbarDismissed) {
        if (isSnackbarDismissed) {
            navController.navigate(Login)
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState, snackbar = { data ->
            Snackbar(snackbarData = data)
        })
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
            Image(
                painter = painterResource(id = R.drawable.logo1negro),
                contentDescription = "Logo",
                modifier = Modifier.size(170.dp)
            )

            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = stringResource(id = R.string.resetPassword),
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.textForgotPassword),
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(320.dp)
            )

            Spacer(modifier = Modifier.height(160.dp))

            TextField(
                value = email,
                onValueChange = { if (it.length <= 30) email = it },
                textStyle = TextStyle(fontSize = 16.sp),
                label = { Text(text = stringResource(id = R.string.email)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Mail, contentDescription = null) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Verde40
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                singleLine = true,
                modifier = Modifier.width(310.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    if (email.isNotEmpty()) {
                        authViewModel.resetPassword(email)
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Introduzca su correo electrónico",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(310.dp)
                    .padding(top = 40.dp)
                    .height(43.dp)
                    .shadow(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BackgroundButtonSignUp,
                    contentColor = BackgroundButtonOpenSession
                )
            ) {
                Text(
                    text = stringResource(id = R.string.recoverPassword),
                    fontSize = 21.sp,
                )
            }
        }
    }
}