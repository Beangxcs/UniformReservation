package com.example.uniformreservation.controller

import com.example.uniformreservation.api.ApiService
import com.example.uniformreservation.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UniformController {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun fetchUniforms() {
        scope.launch {

                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = apiService.getUniform()
                return response;

        }
    }
}
