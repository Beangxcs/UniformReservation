package com.example.uniformreservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.uniformreservation.controller.AuthenticationController
import com.example.uniformreservation.manager.TokenManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var loginbtn: Button
    lateinit var forgotPassword: TextView
    lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        TokenManager.init(applicationContext)
        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginbtn = findViewById(R.id.login_btn)
        forgotPassword = findViewById(R.id.forgot_Password)
        tvRegister = findViewById(R.id.tvRegister)

        loginbtn.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
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



        tvRegister.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
}
