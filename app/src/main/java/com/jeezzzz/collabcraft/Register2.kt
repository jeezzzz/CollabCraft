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

        val log=findViewById<AppCompatButton>(R.id.signup_button)
        log.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val sign=findViewById<AppCompatButton>(R.id.signInButton)
        sign.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()
        var signIn = findViewById<AppCompatButton>(R.id.signInButton)
        signIn.setOnClickListener {
            var emailEditText = findViewById<MaterialAutoCompleteTextView>(R.id.username_ET)
            val email = emailEditText.text.toString()

            val passwordEditText = findViewById<MaterialAutoCompleteTextView>(R.id.password_ET)
            val password = passwordEditText.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        // Navigate to the login activity or home screen
                        val intent=Intent(this,RegisterScreen::class.java)
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
}