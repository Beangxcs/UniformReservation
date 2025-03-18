package com.example.uniformreservation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.uniformreservation.controller.AuthenticationController
import com.example.uniformreservation.manager.TokenManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var forgotPassword: TextView
    private lateinit var tvRegister: TextView
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        TokenManager.init(applicationContext)

        // Initialize views
        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)
        forgotPassword = findViewById(R.id.forgot_Password)
        tvRegister = findViewById(R.id.tvRegister)

        // Set up password visibility toggle
        setupPasswordToggle(passwordInput, R.id.password_input)

        // Login button click listener
        loginBtn.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            login(username, password)
        }

        // Register text click listener
        tvRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }

        // Forgot password (optional: add functionality later)
        forgotPassword.setOnClickListener {
            Toast.makeText(this, "Forgot Password not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun login(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val loginResponse = AuthenticationController.login(username, password)

                if (AuthenticationController.loginSuccess == true) {
                    Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity, HomePage::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, loginResponse, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("Login Error", e.toString())
                Toast.makeText(this@MainActivity, "Something went wrong: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle(editText: EditText, editTextId: Int) {
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // Index for drawableEnd (right)
                if (event.rawX >= (editText.right - editText.compoundDrawables[drawableEnd].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    togglePasswordVisibility(editText, isPasswordVisible)
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
        editText.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.baseline_lock_24, 0, R.drawable.baseline_remove_red_eye_24, 0
        )
        editText.setSelection(editText.text.length) // Move cursor to end
    }
}