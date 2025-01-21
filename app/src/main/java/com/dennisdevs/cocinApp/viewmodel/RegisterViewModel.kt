package com.dennisdevs.cocinApp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import com.dennisdevs.cocinApp.model.data_sources.SessionManager
import com.dennisdevs.cocinApp.model.repositories.AuthRepository
import com.dennisdevs.cocinApp.navigation.RecipeHome
import com.dennisdevs.cocinApp.utils.Validation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val authRepository = AuthRepository(
        context,
        FirebaseAuth.getInstance(),
        FirebaseFirestore.getInstance()
    )

    var email by mutableStateOf("")
    var name by mutableStateOf("")
    var surName by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isChecked by mutableStateOf(false)

    var passwordVisible = mutableStateOf(false)
    var confirmPasswordVisible = mutableStateOf(false)


    fun handleRegisterClick(
        navController: NavHostController,
        snackbarHostState: SnackbarHostState,
        scope: CoroutineScope,
        context: Context
    ) {
        when {
            !Validation.isRegisterValid(email, name, surName, password, confirmPassword) -> {
                showSnackbar(scope, snackbarHostState, "Por favor, complete todos los campos")
            }

            !Validation.isPasswordRequirementsValidate(password) -> {
                val message = Validation.getPasswordValidationMessage(password)
                showSnackbar(scope, snackbarHostState, message)
            }

            !Validation.isPasswordMatch(password, confirmPassword) -> {
                showSnackbar(scope, snackbarHostState, "Las contraseñas no coinciden")
            }

            !isChecked -> {
                showSnackbar(scope, snackbarHostState, "Debe aceptar los términos y condiciones")
            }

            else -> {
                authRepository.registerUser(email, password, name, surName, onSuccess = {},
                    onRegisterSuccess = {
                        SessionManager.setLoggedIn(context, true)
                        scope.launch {
                            showSnackbar(scope, snackbarHostState, "Cuenta creada")
                            delay(700)
                            navController.navigate(RecipeHome)
                        }
                    }, onError = { errorMessage ->
                        showSnackbar(scope, snackbarHostState, errorMessage)
                    })
            }
        }
    }

    private fun showSnackbar(
        scope: CoroutineScope, snackbarHostState: SnackbarHostState, message: String
    ) {
        scope.launch {
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }
}
