package com.example.uniformreservation.controller

import android.util.Log
import com.example.uniformreservation.api.ApiService
import com.example.uniformreservation.api.RetrofitInstance
import com.example.uniformreservation.manager.UserIdManager
import kotlin.math.log

object AuthenticationController {
    var loginSuccess: Boolean? = null

    suspend fun login(username: String, password: String): String {
        return try {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val response = apiService.login(username, password)

            if (response.isSuccessful) {
                val loginResponse = response.body()
                UserIdManager.saveUserId(loginResponse?.user_id ?: "0")
                return if (loginResponse?.user_id != null && loginResponse.message == "Correct Password") {
                    loginSuccess = true
                    "Login successful" // Or use loginResponse.message
                } else {
                    loginSuccess = false
                    "Login failed: ${loginResponse?.message ?: "Unknown error"}"
                }
            } else {
                loginSuccess = false
                Log.d("Login error", "$response")
                "Login failed: ${response.errorBody()?.string() ?: "Server error"}"
            }
        } catch (e: Exception) {
            Log.d("Login error exception", "Exception: $e")
            e.printStackTrace()
            loginSuccess = false
            "Login failed: ${e.message ?: "An unexpected error occurred"}"
        }
    }

    suspend fun register(
        userId: String,
        fullName: String,
        username: String,
        password: String,
        role: String
        ): String {
        return try {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val response = apiService.register(userId, fullName, username, password, role)

            if (response.isSuccessful) {
                response.body()?.message ?: "Registration successful"
            } else {
                "Registration failed: ${response.errorBody()?.string() ?: "Unknown error"}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Registration failed: ${e.message ?: "An unexpected error occurred"}"
        }
    }
}

