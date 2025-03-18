package com.example.uniformreservation.controller

import com.example.uniformreservation.api.ApiService
import com.example.uniformreservation.api.RetrofitInstance
import com.example.uniformreservation.model.Uniform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UniformController {
    private val scope = CoroutineScope(Dispatchers.IO)

    interface UniformCallback {
        fun onSuccess(uniforms: List<Uniform>)
        fun onError(message: String)
    }

    fun fetchUniforms(callback: UniformCallback) {
        scope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = apiService.getUniform()
                val uniforms = response.body()?.rows ?: emptyList()

                // Switch to Main thread for callback
                CoroutineScope(Dispatchers.Main).launch {
                    if (response.isSuccessful) {
                        callback.onSuccess(uniforms)
                    } else {
                        callback.onError("Error: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    callback.onError("Network error")
                }
            }
        }
    }
}