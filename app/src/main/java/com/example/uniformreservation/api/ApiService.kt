package com.example.uniformreservation.api

import com.example.uniformreservation.model.LoginResponse
import com.example.uniformreservation.model.RegisterResponse
import com.example.uniformreservation.model.UniformResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/login.php")
    suspend fun login(
        @Field("email") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("/signup.php")
    suspend fun register(
        @Field("user_id") userId: Int,
        @Field("fullname") fullName: String,
        @Field("email") username: String,
        @Field("password") password: String,
        @Field("role") role: String
    ): Response<RegisterResponse>

    @GET("/uniforms.php")
    suspend fun getUniform(): Response<UniformResponse>

}
