package com.jeezzzz.collabcraft

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

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

        var name=view.findViewById<TextView>(R.id.username)
        var dom=view.findViewById<AppCompatButton>(R.id.applyBtn)
        if (userEmail != null) {
            firestore.collection("users")
                .document(userEmail)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Document exists, extract the branch field and update the Button
                        val userName = document.getString("name")
                        val domain=document.getString("domain1")
                        if(domain!=null){
                            dom.text="$domain"
                        }
                        else{
                            Toast.makeText(requireActivity(), "Failed to load domain", Toast.LENGTH_SHORT)
                                .show()
                        }
                        if (userName != null) {
                            name.text = "$userName"
                        } else{
                            Toast.makeText(requireActivity(), "Failed to load username", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("MyApp", "Error getting user document", exception)
                    // Handle the error
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

        private fun displayPosts(posts: List<Post>?) {
            var cont = view?.findViewById<TextView>(R.id.thread)
            if (posts != null && posts.isNotEmpty()) {
                // Iterate through the posts and do something with them
                for (post in posts) {
                    if (post.content.isNotEmpty()) {
                        cont?.text = post.content
                        // You can display posts in your UI, for example, updating a TextView or RecyclerView
                    }
                }
            } else {
                Log.d("MyApp", "No posts available")
                // Handle the case where there are no posts
            }
        }
    }