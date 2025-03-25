package com.example.uniformreservation.controller

import com.example.uniformreservation.api.ApiService
import com.example.uniformreservation.api.RetrofitInstance
import com.example.uniformreservation.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object UserController {
    private val scope = CoroutineScope(Dispatchers.IO)

    interface UserCallback {
        fun onSuccess(user: User)
        fun onError(message: String)
    }

    fun fetchUser(userId: String, callback: UserCallback) {
        scope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = apiService.getUser(userId)

                // Switch to Main thread for callback
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        if (userResponse != null && userResponse.fullname != null && userResponse.email != null && userResponse.role != null) {
                            // Success case: construct User object
                            val user = User(
                                fullname = userResponse.fullname,
                                email = userResponse.email,
                                role = userResponse.role
                            )
                            callback.onSuccess(user)
                        } else {
                            // Error case: use message and code from response
                            callback.onError(userResponse?.message ?: "Unknown error")
                        }
                    } else {
                        callback.onError("Error: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    callback.onError("Network error: ${e.message}")
                }
            }
        }
    }
}