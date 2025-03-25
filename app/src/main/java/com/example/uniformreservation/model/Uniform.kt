package com.example.uniformreservation.model

data class UniformResponse(
    val message: String,
    val code: Int,
    val rows: List<Uniform>? // Changed from Uniform? to List<Uniform>?
)

data class Uniform(
    val category: String?,
    val name: String?,
    val size: String?,
    val department: String?,
    val image_url: String?,
    val available: Int?
)
