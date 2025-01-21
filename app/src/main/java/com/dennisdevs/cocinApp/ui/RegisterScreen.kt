package com.dennisdevs.cocinApp.ui

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.R
import com.dennisdevs.cocinApp.navigation.TermsConditionsAndPrivacyPolicy
import com.dennisdevs.cocinApp.ui.components.ButtonBack
import com.dennisdevs.cocinApp.ui.components.BackgroundRegisterScreen
import com.dennisdevs.cocinApp.ui.components.MyBottomOptionRegisterScreen
import com.dennisdevs.cocinApp.ui.components.MyCustomTextFieldRegister
import com.dennisdevs.cocinApp.ui.components.MyTermsAndConditions
import com.dennisdevs.cocinApp.ui.components.RegisterButton
import com.dennisdevs.cocinApp.ui.components.WelcomeText
import com.dennisdevs.cocinApp.viewmodel.AuthViewModel
import com.dennisdevs.cocinApp.viewmodel.RegisterViewModel
import com.dennisdevs.cocinApp.viewmodel.RegisterViewModelFactory

@Composable
fun RegisterScreen(navController: NavHostController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    val application = context.applicationContext as Application
    val viewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory(application))
    val focusManager = LocalFocusManager.current
    val confirmPasswordFocusRequester = remember { FocusRequester() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { innerPadding ->

        BackgroundRegisterScreen()
        ButtonBack(navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(145.dp))

            WelcomeText()

            FormContent(
                viewModel = viewModel,
                focusManager = focusManager,
                confirmPasswordFocusRequester = confirmPasswordFocusRequester,
                navController = navController
            )

            RegisterButton(
                onClick = {
                    viewModel.handleRegisterClick(
                        navController, snackbarHostState, scope, context
                    )
                }
            )

            MyBottomOptionRegisterScreen(navController)
        }
    }
}

@Composable
fun FormContent(
    viewModel: RegisterViewModel,
    focusManager: FocusManager,
    confirmPasswordFocusRequester: FocusRequester,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(.70f)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyCustomTextFieldRegister(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = stringResource(id = R.string.email),
            leadingIcon = Icons.Default.Mail,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )

        MyCustomTextFieldRegister(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = stringResource(id = R.string.name),
            leadingIcon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        MyCustomTextFieldRegister(
            value = viewModel.surName,
            onValueChange = { viewModel.surName = it },
            label = stringResource(id = R.string.last_name),
            leadingIcon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )

        MyCustomTextFieldRegister(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = stringResource(id = R.string.password),
            leadingIcon = Icons.Default.Lock,
            isPasswordField = true,
            passwordVisible = viewModel.passwordVisible
        )

        MyCustomTextFieldRegister(
            value = viewModel.confirmPassword,
            onValueChange = { viewModel.confirmPassword = it },
            label = stringResource(id = R.string.confirm_password),
            leadingIcon = Icons.Default.Lock,
            isPasswordField = true,
            passwordVisible = viewModel.confirmPasswordVisible,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.focusRequester(confirmPasswordFocusRequester)
        )

        MyTermsAndConditions(
            value = stringResource(id = R.string.terms_and_conditions),
            onTextSelected = { navController.navigate(TermsConditionsAndPrivacyPolicy) },
            onCheckedChange = { viewModel.isChecked = it }
        )
    }
}