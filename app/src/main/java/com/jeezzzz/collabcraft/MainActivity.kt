package com.jeezzzz.collabcraft

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.content.Context
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var signup=findViewById<AppCompatButton>(R.id.signup_button)
        signup.setOnClickListener{
            intent=Intent(this, Register2::class.java)
            startActivity(intent)
        }

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        var loginButton=findViewById<AppCompatButton>(R.id.signInButton)
        var emailEditText=findViewById<MaterialAutoCompleteTextView>(R.id.username_ET)
        var passwordEditText=findViewById<MaterialAutoCompleteTextView>(R.id.password_ET)


        if (isLoggedIn()) {
            navigateToHomeScreen()
        }

        loginButton.setOnClickListener {

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login successful
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                        // Navigate to the home screen
                        intent = Intent(this, SecondActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Login failed
                        Toast.makeText(
                            this,
                            "Login failed. ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
        }
    }
    private fun isLoggedIn(): Boolean {
        val prefs: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return prefs.getString("uid", null) != null
    }

    private fun saveUIDToPrefs() {
        val prefs: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val uid = auth.currentUser?.uid
        prefs.edit().putString("uid", uid).apply()
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
        finish()
    }
}
