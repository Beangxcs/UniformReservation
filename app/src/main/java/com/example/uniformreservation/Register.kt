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

class Register : AppCompatActivity() {

    private lateinit var etLogin: TextView
    private lateinit var etFullName: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var etUserId: EditText
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false
    private lateinit var roleSpinner: Spinner

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
        etUserId = findViewById(R.id.userid_input)

        val roles = listOf("Student", "Admin")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter

        etLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set up password visibility toggle
        setupPasswordToggle(etPassword, R.id.password)
        setupPasswordToggle(etConfirmPassword, R.id.confirmpassword_input)

        btnRegister.setOnClickListener {
            val userId = etUserId.text.toString().trim()
            val fullName = etFullName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            val role = roleSpinner.selectedItem.toString().lowercase()

            if (userId.isEmpty() || fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (username.length <= 5) {
                Toast.makeText(this, "Email should be at least 5 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8) {
                Toast.makeText(this, "Password should be at least 8 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (containsNumber(fullName)) {
                Toast.makeText(this, "Name should not contain numerical value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val result = withContext(Dispatchers.IO) {
                    register(userId.toInt(), fullName, username, password, role)
                }

                Toast.makeText(this@Register, result, Toast.LENGTH_SHORT).show()

                if (result.contains("successful", ignoreCase = true)) {
                    val intent = Intent(this@Register, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun containsNumber(input: String): Boolean {
        return input.any { it.isDigit() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle(editText: EditText, editTextId: Int) {
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // Index for drawableEnd (right)
                if (event.rawX >= (editText.right - editText.compoundDrawables[drawableEnd].bounds.width())) {
                    if (editTextId == R.id.password) {
                        isPasswordVisible = !isPasswordVisible
                        togglePasswordVisibility(editText, isPasswordVisible)
                    } else if (editTextId == R.id.confirmpassword_input) {
                        isConfirmPasswordVisible = !isConfirmPasswordVisible
                        togglePasswordVisibility(editText, isConfirmPasswordVisible)
                    }
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility(editText: EditText, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        // Keep the eye icon consistent (optional: change icon if desired)
        editText.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.baseline_lock_24, 0, R.drawable.baseline_remove_red_eye_24, 0
        )
        editText.setSelection(editText.text.length) // Move cursor to end
    }
}