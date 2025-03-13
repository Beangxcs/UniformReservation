package com.example.uniformreservation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.uniformreservation.model.Uniform

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample Data
        val uniformList = listOf(
            Uniform(R.drawable.citelogo, "Uniform A", "Comfortable cotton uniform", "S, M, L", "Available"),
            Uniform(R.drawable.citelogo, "Uniform B", "Polyester blend, durable", "M, L, XL", "Limited"),
            Uniform(R.drawable.citelogo, "Uniform C", "Lightweight and breathable", "S, M", "Out of Stock")
        )

        // Set Adapter
        val adapter = HomeAdapter(uniformList)
        recyclerView.adapter = adapter

        // Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_home // Highlight the current tab

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> true // Already here
                R.id.nav_categories -> {
                    startActivity(Intent(this, Categories::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, Profile::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
