package com.jeezzzz.collabcraft

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var signUp=findViewById<Button>(R.id.signup_button)

        signUp.setOnClickListener{
            val intent = Intent(this, Register2::class.java)
            startActivity(intent)
        }

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        // Login button click listener
        val loginButton=findViewById<AppCompatButton>(R.id.signInButton)
        loginButton.setOnClickListener {
            val emailEditText=findViewById<AutoCompleteTextView>(R.id.username_ET)
            val email = emailEditText.text.toString()
            val passwordEditText=findViewById<AutoCompleteTextView>(R.id.password_ET)
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login successful
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                        // Navigate to the home screen

                    } else {
                        // Login failed
                        Toast.makeText(this, "Login failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}