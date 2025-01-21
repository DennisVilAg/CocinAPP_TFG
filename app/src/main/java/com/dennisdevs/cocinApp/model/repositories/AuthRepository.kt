package com.dennisdevs.cocinApp.model.repositories

import android.content.Context
import com.dennisdevs.cocinApp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository(
    private val context: Context,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val sessionRepository = SessionRepository(context)

    //-----CREATE A CLIENT SESSION GOOGLE-----
    val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.clientId))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    //----EMAIL AND PASSWORD----
    fun loginWithEmailPassword(
        email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sessionRepository.setLoggedIn(true)
                onSuccess()
            } else {
                val exception = task.exception as? FirebaseAuthException
                val errorCode = exception?.errorCode ?: "unknown_error"
                onError(translateFirebaseError(errorCode))
            }
        }
    }

    //-----NEW USER-----
    fun registerUser(
        email: String,
        password: String,
        name: String,
        surName: String,
        onSuccess: () -> Unit,
        onRegisterSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val userId = user?.uid ?: return@addOnCompleteListener
                sessionRepository.setLoggedIn(true)
                sessionRepository.setUserId(userId)

                val userMap = mapOf(
                    "name" to name, "surName" to surName, "email" to email
                )

                firestore.collection("users").document(userId).set(userMap).addOnSuccessListener {
                    onRegisterSuccess()
                    onSuccess()
                }.addOnFailureListener { exception ->
                    onError(exception.message ?: "Error al guardar los datos del usuario")
                }
            } else {
                val exception = task.exception as? FirebaseAuthException
                if (exception is FirebaseAuthException) {
                    val errorCode = exception.errorCode
                    onError(translateFirebaseError(errorCode))
                } else {
                    onError("Error desconocido. Inténtalo de nuevo.")
                }
            }
        }
    }

    //-----GET USER DATA-----
    fun getUserData(
        onSuccess: (String, String, String) -> Unit,
        onError: (String) -> Unit
    ) {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("name") ?: "No disponible"
                        val surName = document.getString("surName") ?: "No disponible"
                        val email = document.getString("email") ?: "No disponible"
                        onSuccess(name, surName, email)
                    } else {
                        onError("Usuario no encontrado en Firestore")
                    }
                }
                .addOnFailureListener { exception ->
                    onError("Error al obtener los datos: ${exception.message}")
                }
        } else {
            onError("No hay usuario autenticado")
        }
    }

    //-----CLOSE SESSION-----
    fun signOut() {
        auth.signOut()
        sessionRepository.setLoggedIn(false)
    }

    //-----RESET PASSWORD---
    fun resetPassword(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError("Error al enviar el correo. Verifique el correo ingresado.")
            }
        }
    }

    //----LOGIN WITH GOOGLE
    fun loginWithGoogle(idToken: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sessionRepository.setLoggedIn(true)
                onSuccess()
            } else {
                val exception = task.exception
                onError(exception?.message ?: "Error de autenticación con Google")
            }
        }
    }

    //-----TRANSLATES-----
    fun translateFirebaseError(errorCode: String): String {
        return when (errorCode) {
            "ERROR_INVALID_EMAIL" -> "Email mal formateado."
            "ERROR_INVALID_CREDENTIAL" -> "Correo o contraseña incorrectos."
            "ERROR_EMAIL_ALREADY_IN_USE" -> "El correo electrónico ya está registrado. Intenta iniciar sesión."
            else -> "Error de autenticación. Inténtalo de nuevo."
        }
    }
}