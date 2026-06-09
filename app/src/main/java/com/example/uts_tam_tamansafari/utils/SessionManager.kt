package com.example.uts_tam_tamansafari.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("distriagri_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_IS_LOGIN = "is_login"
        private const val KEY_LOCAL_USER = "local_username"
        private const val KEY_LOCAL_PASS = "local_password"
        private const val KEY_LOCAL_EMAIL = "local_email"
        private const val KEY_USER_DETAIL_NAME = "user_detail_name"
    }

    fun saveAuthToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).putBoolean(KEY_IS_LOGIN, true).apply()
    }

    fun saveUserDetail(username: String, fullName: String) {
        prefs.edit().putString(KEY_LOCAL_USER, username).putString(KEY_USER_DETAIL_NAME, fullName).apply()
    }

    fun saveLocalRegister(username: String, pass: String, email: String = "") {
        prefs.edit()
            .putString(KEY_LOCAL_USER, username)
            .putString(KEY_LOCAL_PASS, pass)
            .putString(KEY_LOCAL_EMAIL, email)
            .apply()
    }

    fun getLocalUsername(): String? = prefs.getString(KEY_LOCAL_USER, null)
    fun getLocalPassword(): String? = prefs.getString(KEY_LOCAL_PASS, null)
    fun getFullName(): String? = prefs.getString(KEY_USER_DETAIL_NAME, prefs.getString(KEY_LOCAL_USER, "User"))
    fun getUserName(): String? = prefs.getString(KEY_LOCAL_USER, "Username")
    fun getEmail(): String? = prefs.getString(KEY_LOCAL_EMAIL, "user@distriagri.com")

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGIN, false)

    fun logout() {
        prefs.edit().clear().apply()
    }
}