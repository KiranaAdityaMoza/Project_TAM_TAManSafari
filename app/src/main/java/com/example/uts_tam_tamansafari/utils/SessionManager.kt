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
        private const val KEY_LOGIN_TIMESTAMP = "login_timestamp"

        private const val SESSION_DURATION = 30L * 24L * 60L * 60L * 1000L
    }

    fun saveAuthToken(token: String) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putBoolean(KEY_IS_LOGIN, true)
            .putLong(KEY_LOGIN_TIMESTAMP, System.currentTimeMillis())
            .apply()
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

    fun isLoggedIn(): Boolean {
        val isLogin = prefs.getBoolean(KEY_IS_LOGIN, false)
        if (!isLogin) return false

        val loginTime = prefs.getLong(KEY_LOGIN_TIMESTAMP, 0L)
        val currentTime = System.currentTimeMillis()

        if ((currentTime - loginTime) > SESSION_DURATION) {
            logout()
            return false
        }

        return true
    }

    fun logout() { 
        prefs.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_LOGIN_TIMESTAMP)
            .putBoolean(KEY_IS_LOGIN, false)
            .apply()
    }
}