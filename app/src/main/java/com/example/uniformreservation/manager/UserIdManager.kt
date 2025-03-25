package com.example.uniformreservation.manager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object UserIdManager {
    private const val PREFS_NAME = "user_id"
    private const val KEY = "id"
    private lateinit var preferences: SharedPreferences

    // Initialize SharedPreferences with the app context
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Save the userId as the value under the KEY
    fun saveUserId(userId: String) {
        preferences.edit().putString(KEY, userId).apply() // Save userId as value
        Log.d("UserIdManager", "Saved user ID: $userId")
    }

    // Retrieve the userId stored under KEY
    fun getUserId(): String? { // Changed return type to String? to handle null case
        val userId = preferences.getString(KEY, null) // Default to null if not found
        Log.d("UserIdManager", "Getting user ID: $userId")
        return userId
    }

    // Clear the userId from SharedPreferences
    fun clearUserId() {
        preferences.edit().remove(KEY).apply()
        Log.d("UserIdManager", "Cleared user ID")
    }

    // Check if a valid userId is stored
    fun isLoggedIn(): Boolean {
        val userId = getUserId()
        return userId != null && userId != "0" // Check for null and "0"
    }
}