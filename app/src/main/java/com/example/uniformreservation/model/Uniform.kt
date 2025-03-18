package com.example.uniformreservation.model

data class UniformResponse(
    val message: String,
    val code: Int,
    val rows: List<Uniform>?
)

data class Uniform(
    val id: Int? = null,
    val category: String,
    val name: String,
    val image_url: String,
    val size: String,
    val department: String,
    val available: Int? = null
)
