package com.example.uniformreservation.model

import com.google.gson.annotations.SerializedName

data class Login (
    val username: String,
    val password: String
)

data class LoginResponse (
    val token: String,
    val message: String
)

data class Register (
    @SerializedName("first_name")
    val fullName: String,
    val username: String,
    val password: String,
    val email: String
)