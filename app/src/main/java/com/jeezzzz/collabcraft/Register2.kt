package com.jeezzzz.collabcraft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.auth.FirebaseAuth

class Register2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        var log = findViewById<AppCompatButton>(R.id.signup_button)
        log.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        var registerButton = findViewById<AppCompatButton>(R.id.signInButton)
        var emailEditText = findViewById<MaterialAutoCompleteTextView>(R.id.username_ET)
        var passwordEditText = findViewById<MaterialAutoCompleteTextView>(R.id.password_ET)
        // Registration button click listener
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                        saveUserCredentials(email, password)

                        // Navigate to the login activity or home screen
                        intent = Intent(this, RegisterScreen::class.java)
                        startActivity(intent)
                    } else {
                        // Registration failed
                        Toast.makeText(
                            this,
                            "Registration failed. ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun saveUserCredentials(email: String, password: String) {
        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        prefs.edit().apply {
            putString("user_email", email)
            putString("user_password", password)
            apply()
        }
    }
}