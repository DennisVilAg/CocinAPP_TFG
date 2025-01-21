package com.dennisdevs.cocinApp.utils

import android.util.Log

class Validation {
    companion object {
        private fun isValid(value: String): Boolean {
            val isValid = value.isNotBlank()
            return isValid
        }

        fun isLoginValid(email: String, password: String): Boolean {
            if (!isValidEmail(email)) {
                Log.d("LoginActivity", "Correo electrónico mal formateado")
                return false
            }
            return isValid(password)
        }

        // Función para validar el formato del correo electrónico
        fun isValidEmail(email: String): Boolean {
            // Regex para verificar formato
            val emailRegex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}".toRegex()
            return email.matches(emailRegex)
        }


        fun isRegisterValid(
            email: String,
            name: String,
            surName: String,
            password: String,
            confirmPassword: String
        ): Boolean {
            return isValid(email) && isValid(name) && isValid(surName) && isValid(password) && isValid(
                confirmPassword
            )
        }

        fun isPasswordMatch(password: String, confirmPassword: String): Boolean {
            return password == confirmPassword
        }

        fun isPasswordRequirementsValidate(password: String): Boolean {
            return password.length in 8..25 &&
                    password.any { it.isLowerCase() } &&
                    password.any { it.isUpperCase() } &&
                    password.any { it.isDigit() } &&
                    password.any { !it.isLetterOrDigit() }
        }

        fun getPasswordValidationMessage(password: String): String {
            val issues = mutableListOf<String>()

            if (password.length !in 8..25) {
                issues.add("entre 8 y 25 caracteres")
            }
            if (!password.any { it.isLowerCase() }) {
                issues.add("al menos una letra minúscula")
            }
            if (!password.any { it.isUpperCase() }) {
                issues.add("al menos una letra mayúscula")
            }
            if (!password.any { it.isDigit() }) {
                issues.add("al menos un número")
            }
            if (!password.any { !it.isLetterOrDigit() }) {
                issues.add("al menos un carácter especial")
            }

            return if (issues.isEmpty()) {
                "Contraseña válida"
            } else {
                "La contraseña debe contener: ${issues.joinToString(", ")}."
            }
        }
    }
}