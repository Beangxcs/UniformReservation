package com.example.uniformreservation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.uniformreservation.controller.UserController
import com.example.uniformreservation.manager.UserIdManager
import com.example.uniformreservation.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class Profile : AppCompatActivity() {
    private lateinit var etFullname: TextView
    private lateinit var etEmail: TextView
    private lateinit var etRole: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        etFullname = findViewById(R.id.et_fullname)
        etEmail = findViewById(R.id.et_email)
        etRole = findViewById(R.id.et_role)
        btnLogout = findViewById(R.id.btnLogout)

        // Logout button
        btnLogout.setOnClickListener {
            UserIdManager.clearUserId()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_profile
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomePage::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.nav_profile -> true // Already here
                else -> false
            }
        }

        // Fetch and display user data
        val userId = UserIdManager.getUserId()
        if (userId != null) {
            fetchUserData(userId)
        } else {
            etFullname.text = "Not logged in"
            etEmail.text = "N/A"
            etRole.text = "N/A"
        }
    }

    private fun fetchUserData(userId: String) {
        UserController.fetchUser(userId, object : UserController.UserCallback {
            override fun onSuccess(user: User) {
                etFullname.text = user.fullname
                etEmail.text = user.email
                etRole.text = user.role
            }

            override fun onError(message: String) {
                etFullname.text = "Error"
                etEmail.text = message
                etRole.text = "N/A"
            }
        })
    }
}