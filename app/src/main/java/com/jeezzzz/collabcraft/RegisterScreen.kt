package com.jeezzzz.collabcraft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class RegisterScreen : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var year: String
    private lateinit var branch: String
    private lateinit var domain1: String
    private lateinit var domain2: String
    private lateinit var domain3: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        val editTextName: EditText = findViewById(R.id.nameEt)
        val editTextEmail: EditText = findViewById(R.id.collegeIdEt)
        val editTextYear: EditText = findViewById(R.id.yearIdEt)
        val editTextBranch: EditText = findViewById(R.id.branchEt)
        val editTextDomain1: EditText = findViewById(R.id.firstDomainEt)
        val editTextDomain2: EditText = findViewById(R.id.secondDomainEt)
        val editTextDomain3: EditText = findViewById(R.id.thirdDomainEt)

        val submitButton = findViewById<AppCompatButton>(R.id.submit)

        val db = FirebaseFirestore.getInstance()

        submitButton.setOnClickListener {
            // Get text from EditText views
            name = editTextName.text.toString()
            email = editTextEmail.text.toString()
            year = editTextYear.text.toString()
            branch = editTextBranch.text.toString()
            domain1 = editTextDomain1.text.toString()
            domain2 = editTextDomain2.text.toString()
            domain3 = editTextDomain3.text.toString()

            // Now 'name', 'email', 'year', 'branch', 'domain1', 'domain2', and 'domain3' contain user inputs
            // You can use these variables to perform further actions, validations, or store in Firebase

// Assuming user inputs are collected in variables like name, email, year, branch, domain1, domain2, domain3

            val user = Users(name, email, year, branch, domain1, domain2, domain3)

// Add user data to Firestore
            db.collection("users")
                .document(email) // Using email as the document ID
                .set(user)
                .addOnSuccessListener {
                    // Data successfully added to Firestore
                    Toast.makeText(this, "User data added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    Toast.makeText(this, "Error adding user data: $e", Toast.LENGTH_SHORT).show()
                }
        }
    }
}