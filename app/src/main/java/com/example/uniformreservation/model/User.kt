package com.example.uniformreservation.model

data class UserResponse(
    val message: String? = null,
    val code: Int? = null,
    val fullname: String? = null,
    val email: String? = null,
    val role: String? = null
)

data class User(
    val fullname: String,
    val email: String,
    val role: String
)