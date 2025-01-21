package com.dennisdevs.cocinApp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dennisdevs.cocinApp.model.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository(
        application.applicationContext,
        FirebaseAuth.getInstance(),
        FirebaseFirestore.getInstance()
    )

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent: SharedFlow<String> = _navigationEvent

    // ----LOGIN WITH GOOGLE----
    fun loginWithGoogle(idToken: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        authRepository.loginWithGoogle(idToken, onSuccess, onError)
    }

    // ----GET USER DATA----
    fun getUserData(onSuccess: (String, String, String) -> Unit, onError: (String) -> Unit) {
        authRepository.getUserData(onSuccess, onError)
    }

    // -----LOG OUT-----
    fun signOut() {
        authRepository.signOut()
    }

    // -----RESET PASSWORD-----
    fun resetPassword(email: String) {
        authRepository.resetPassword(
            email,
            onSuccess = {
                _snackbarMessage.value = "Si esta registrado se enviará el restablecimiento de la contraseña."
                viewModelScope.launch {
                    _navigationEvent.emit("navigate_to_login")
                }
            },
            onError = { message ->
                _snackbarMessage.value = message
            }
        )
    }

    // -----CLEAN SNACKBAR-----
    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}