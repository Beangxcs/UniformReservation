package com.example.uniformreservation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.uniformreservation.controller.AuthenticationController.register
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.compareTo

class Register : AppCompatActivity() {

    private lateinit var etLogin: TextView
    private lateinit var etFullName: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false
    private lateinit var roleSpinner: Spinner
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        etLogin = findViewById(R.id.tvLogin)
        etFullName = findViewById(R.id.fullname_input)
        etUsername = findViewById(R.id.username_input)
        etPassword = findViewById(R.id.password)
        etConfirmPassword = findViewById(R.id.confirmpassword_input)
        btnRegister = findViewById(R.id.register_button)
        roleSpinner = findViewById(R.id.role_spinner)

        // 🟢 1. Define your roles
        val roles = listOf("User", "Admin")

        // 🟢 2. Create an ArrayAdapter and set it to the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter

        etLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegister.setOnClickListener {
            val userId = 0 // or handle this properly
            val fullName = etFullName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            val role = roleSpinner.selectedItem.toString().lowercase() // Gets selected role

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val result = withContext(Dispatchers.IO) {
                    register(userId, fullName, username, password, role)
                }

                Toast.makeText(this@Register, result, Toast.LENGTH_SHORT).show()

                if (result.contains("successful", ignoreCase = true)) {
                    val intent = Intent(this@Register, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        // 👁 Password visibility toggle listeners...
        // (Keep your toggle handlers here as you already have them)
    }


    private fun togglePasswordVisibility(editText: EditText, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.baseline_lock_24, 0, R.drawable.baseline_remove_red_eye_24, 0
            )
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.baseline_lock_24, 0, R.drawable.baseline_remove_red_eye_24, 0
            )
        }
        // Move cursor to the end after toggling
        editText.setSelection(editText.text.length)
    }
}


