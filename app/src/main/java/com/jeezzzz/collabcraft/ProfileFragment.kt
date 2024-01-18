package com.jeezzzz.collabcraft

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.ai.client.generativeai.type.content
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.Query
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private lateinit var threadTextView: TextView


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val use = view.findViewById<TextView>(R.id.name)
        val name = view.findViewById<TextView>(R.id.username_thread)
        val n = view.findViewById<TextView>(R.id.username)
        val content = view.findViewById<TextView>(R.id.thread)
        val dom = view.findViewById<Button>(R.id.domain)



        val br=view.findViewById<Button>(R.id.branch)

        var userEmail = FirebaseAuth.getInstance().currentUser?.email

        val firestore = FirebaseFirestore.getInstance()

        if (userEmail != null) {
            firestore.collection("users")
                .document(userEmail)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // User document exists, extract the posts field
                        val user = documentSnapshot.toObject(Users::class.java)
                        val posts = user?.posts

                        // Display posts in the UI (replace this with your logic)
                        displayPosts(posts)
                    } else {
                        Log.d("MyApp", "User document does not exist")
                        // Handle the case where the user document does not exist
                    }
                }

                .addOnFailureListener { exception ->
                    Log.e("MyApp", "Error getting user document", exception)
                    // Handle the error
                }
        }

        if (userEmail != null) {
            firestore.collection("users")
                .document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Document exists, extract the branch field and update the Button
                        val userDomain = document.getString("domain1")
                        if (br != null) {
                            dom.text = "• $userDomain"
                        } else {
                            Log.e("MyApp", "Button not found or not initialized properly")
                        }
                    }
                }
        }

        if (userEmail != null) {
            firestore.collection("users")
                .document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Document exists, extract the branch field and update the Button
                        val userBranch = document.getString("branch")
                        if (br != null) {
                            br.text = userBranch
                        } else {
                            Log.e("MyApp", "Button not found or not initialized properly")
                        }
                    }
                }
        }

        val yr=view.findViewById<Button>(R.id.year)

        if (userEmail != null) {
            firestore.collection("users")
                .document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    val userYear = document.getString("year")
                    if (br != null) {
                        yr.text = "year:-$userYear"
                    } else {
                        Log.e("MyApp", "Button not found or not initialized properly")
                    }
                }
        }

        if (userEmail != null) {
            firestore.collection("users")
                .document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Document exists, extract the name field and update the TextView
                        val userName = document.getString("name")
                        Log.d("MyApp", "userName from Firestore: $userName")
                        use?.text = userName
                        name?.text = userName
                        n?.text = "@$userName"
                    } else {
                        Log.d("MyApp", "Document does not exist or is empty")
                        Toast.makeText(requireActivity(), "Failed to load user", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }



        firestore.collection("users")
            .document(userEmail!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Document exists, extract the name field and update the TextView
                    val userName = document.getString("name")
                    Log.d("MyApp", "userName from Firestore: $userName")
                    use.text = userName
                } else {
                    Log.d("MyApp", "Document does not exist or is empty")
                    Toast.makeText(requireActivity(), "Failed to load user", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        return view
    }
        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment EmailFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
    private fun displayPosts(posts: List<Post>?) {
        var cont=view?.findViewById<TextView>(R.id.thread)
        if (posts != null && posts.isNotEmpty()) {
            // Iterate through the posts and do something with them
            for (post in posts) {
                if (post.content.isNotEmpty()) {
                    cont?.text=post.content
                    // You can display posts in your UI, for example, updating a TextView or RecyclerView
                }
            }
        } else {
            Log.d("MyApp", "No posts available")
            // Handle the case where there are no posts
        }
    }
    }
