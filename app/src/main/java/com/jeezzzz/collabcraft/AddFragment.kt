package com.jeezzzz.collabcraft

import DomainAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddFragment : Fragment() {

    private lateinit var editText: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Replace R.layout.fragment_add with your actual layout resource ID
        val view = inflater.inflate(R.layout.fragment_add, container, false)


        editText = view.findViewById(R.id.post)
        val use=view.findViewById<TextView>(R.id.username)

        var userEmail = FirebaseAuth.getInstance().currentUser?.email
        val firestore = FirebaseFirestore.getInstance()


        if (userEmail != null) {
            firestore.collection("users")
                .document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Document exists, extract the name field and update the TextView
                        val userName = document.getString("name")
                        Log.d("MyApp", "userName from Firestore: $userName")
                        use.text = userName
                    } else {
                        Log.d("MyApp", "Document does not exist or is empty")
                        Toast.makeText(requireActivity(), "Failed to load user", Toast.LENGTH_SHORT).show()
                    }
                }
        }



        // Code for the domain spinner
        val domainItems = ArrayList<DomainItem>()
        domainItems.add(DomainItem("• ML"))
        domainItems.add(DomainItem("• Android"))
        domainItems.add(DomainItem("• WebD"))
        domainItems.add(DomainItem("• ARVR"))
        // Add more domains as needed

        val domainAdapter = DomainAdapter(requireContext(), domainItems)
        val domainSpinner: Spinner = view.findViewById(R.id.spinner_domain)
        domainSpinner.adapter = domainAdapter

        domainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val clickedItem = parent?.getItemAtPosition(position) as DomainItem
                val domain = clickedItem.domainName
                // Add logic to filter and display posts based on the selected domain
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val yourButton = view.findViewById<AppCompatButton>(R.id.postButton)
        yourButton.setOnClickListener {
            onPostButtonClick()
        }
        return view
    }

    private fun onPostButtonClick() {
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            // Get the current user's email address
            val userEmail = FirebaseAuth.getInstance().currentUser?.email

            // Check if the user is signed in
            if (userEmail != null) {
                // Update the user's posts in Firestore
                addPostToUserInFirestore(userEmail, text)
            } else {
                // Handle the case where the user is not signed in
                Toast.makeText(
                    requireActivity(),
                    "User not signed in",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // Handle empty post case
            Toast.makeText(requireActivity(), "Please enter post content", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addPostToUserInFirestore(userEmail: String, postContent: String) {
        val firestore = FirebaseFirestore.getInstance()
        val userDocumentReference = firestore.collection("users").document(userEmail)

        // Check if the user document exists
        userDocumentReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot.exists()) {
                    // User document exists, proceed with adding the post
                    val user = documentSnapshot.toObject(Users::class.java)
                    val newPost = Post(postContent)
                    user?.posts?.add(newPost)

                    // Update the user document in Firestore
                    userDocumentReference.set(user!!)
                        .addOnSuccessListener {
                            // Post added to the user successfully
                            Toast.makeText(
                                requireActivity(),
                                "Post added to the user",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            // Handle failure
                            Toast.makeText(
                                requireActivity(),
                                "Failed to add post to the user",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    // User document does not exist, create it
                    val newUser = Users() // Create a new user with default values
                    val newPost = Post(postContent)
                    newUser.posts = arrayListOf(newPost)

                    firestore.collection("users").document(userEmail)
                        .set(newUser)
                        .addOnSuccessListener {
                            // User document created, now add the post
                            Toast.makeText(
                                requireActivity(),
                                "User document created. Adding post...",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Call the function recursively to add the post after creating the user document
                            addPostToUserInFirestore(userEmail, postContent)
                        }
                        .addOnFailureListener {
                            // Handle failure
                            Toast.makeText(
                                requireActivity(),
                                "Failed to create user document",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            } else {
                // Handle failure
                Toast.makeText(
                    requireActivity(),
                    "Failed to check user document existence",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}