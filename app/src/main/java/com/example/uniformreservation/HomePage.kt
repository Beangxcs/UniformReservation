package com.example.uniformreservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniformreservation.controller.UniformController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.uniformreservation.model.Uniform

class HomePage : AppCompatActivity() {
    private val uniformController = UniformController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Set an empty adapter initially to avoid "No adapter attached" warning
        recyclerView.adapter = HomeAdapter(emptyList())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_home

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, Profile::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }

        loadUniforms(recyclerView)
    }

    private fun loadUniforms(recyclerView: RecyclerView) {
        uniformController.fetchUniforms(object : UniformController.UniformCallback {
            override fun onSuccess(uniforms: List<Uniform>) {
                recyclerView.adapter = HomeAdapter(uniforms)
                Log.d("UniformController", "Uniforms loaded successfully: $uniforms")
            }

            override fun onError(message: String) {
                Log.e("UniformController", "Error loading uniforms: $message")
            }
        })
    }
}