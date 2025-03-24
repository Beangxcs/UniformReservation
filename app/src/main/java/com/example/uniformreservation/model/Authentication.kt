package com.example.uniformreservation.model

data class LoginResponse (
    val user_id: Int?,
    val message: String,
    val code: Int
)
data class RegisterResponse (
    val message: String
)