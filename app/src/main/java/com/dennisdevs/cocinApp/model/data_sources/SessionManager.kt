package com.dennisdevs.cocinApp.model.data_sources

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    // Agregar clave para guardar el UserID
    private const val KEY_USER_ID = "user_id"

    // Guardar el UserID
    fun setUserId(context: Context, userId: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    // Obtener el UserID
    fun getUserId(context: Context): String? {
        return getPreferences(context).getString(KEY_USER_ID, null)
    }

    // Definir el nombre del archivo SharedPreferences donde se guardarán los datos
    private const val PREF_NAME = "user_session"

    // Definir la clave con la que guardaremos el estado de sesión
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    // Método privado para obtener el objeto SharedPreferences
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    //  guardar el estado de inicio de sesión
    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply() // Aplicar cambios asincrónicamente
    }

    //obtener el estado de inicio de sesión
    fun isLoggedIn(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    //borrar los datos de sesión
    fun clearSession(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()  // Eliminar todas las preferencias
        editor.apply()  // Aplicar cambios
    }
}
