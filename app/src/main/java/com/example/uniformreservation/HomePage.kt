package com.example.uniformreservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniformreservation.controller.UniformController
import com.example.uniformreservation.model.Uniform
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {
    private val uniformController = UniformController()
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinnerCategories: Spinner
    private var allUniforms: List<Uniform> = emptyList() // Stores all uniforms for filtering

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HomeAdapter(emptyList())

        // Initialize Spinner
        spinnerCategories = findViewById(R.id.spinnerCategories)

        // Set Bottom Navigation
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

        loadUniforms()
    }

    private fun loadUniforms() {
        uniformController.fetchUniforms(object : UniformController.UniformCallback {
            override fun onSuccess(uniforms: List<Uniform>) {
                allUniforms = uniforms
                updateRecyclerView(allUniforms)

                // Extract unique categories
                val categories = listOf("All") + allUniforms.map { it.category }.distinct()

                // Set up Spinner with categories
                val adapter = ArrayAdapter(this@HomePage, android.R.layout.simple_spinner_item, categories)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCategories.adapter = adapter

                // Handle category selection
                spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val selectedCategory = categories[position]
                        if (selectedCategory != null) {
                            filterByCategory(selectedCategory)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            override fun onError(message: String) {
                Log.e("UniformController", "Error loading uniforms: $message")
            }
        })
    }

    private fun filterByCategory(category: String) {
        val filteredUniforms = if (category == "All") {
            allUniforms
        } else {
            allUniforms.filter { it.category == category }
        }
        updateRecyclerView(filteredUniforms)
    }

    private fun updateRecyclerView(uniforms: List<Uniform>) {
        recyclerView.adapter = HomeAdapter(uniforms)
    }
}
