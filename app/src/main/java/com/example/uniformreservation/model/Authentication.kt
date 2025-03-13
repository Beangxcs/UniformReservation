package com.example.uniformreservation.model

import com.google.gson.annotations.SerializedName

data class Login (
    val username: String,
    val password: String
)

data class LoginResponse (
    val message: String
)

data class Register (
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("fullname")
    val fullName: String,
    val username: String,
    val password: String,
    val role: String
)

data class RegisterResponse (
    val message: String
)