package com.example.uniformreservation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    }
}
