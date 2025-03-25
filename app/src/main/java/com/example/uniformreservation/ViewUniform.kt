package com.example.uniformreservation

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load

class ViewUniform : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_uniform)

        // Find views
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toolbarIcon = findViewById<ImageView>(R.id.toolbar_icon)
        val toolbarTitle = findViewById<TextView>(R.id.tv_profile)
        val imageView = findViewById<ImageView>(R.id.myImageView)
        val nameEditText = findViewById<EditText>(R.id.Etcorpomale)
        val descriptionTextView = findViewById<TextView>(R.id.tvDescription)
        val sizeAvailableTextView = findViewById<TextView>(R.id.tvSizeAvailable)

        // Retrieve extras from Intent
        val imageUrl = intent.getStringExtra("image")
        val totalAvailable = intent.getIntExtra("totalAvailable", 0)
        val name = intent.getStringExtra("name")
        val size = intent.getStringExtra("size")
        val department = intent.getStringExtra("department")

        // Set up toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide default title
        toolbarTitle.text = name ?: "Uniform Details" // Use name as title, fallback if null
        toolbarIcon.setOnClickListener {
            finish() // Back button functionality
        }

        // Populate views
        imageView.load(imageUrl) // Load image with Coil
        nameEditText.setText(name ?: "Unknown") // Set uniform name
        descriptionTextView.text = "Department: ${department ?: "Unknown"}" // Use department in description
        sizeAvailableTextView.text = "Size: ${size ?: "N/A"}, Available: $totalAvailable" // Show size and availability

        // Handle window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}