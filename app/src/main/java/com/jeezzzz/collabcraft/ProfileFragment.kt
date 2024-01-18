package com.jeezzzz.collabcraft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
        return inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize your views
        threadTextView = (view?.findViewById(R.id.thread) ?:

        // Retrieve the most recent post from Firestore
        getMostRecentPost()) as TextView

    }

    private fun getMostRecentPost() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        if (userEmail != null) {
            val firestore = FirebaseFirestore.getInstance()

            // Assuming you have a collection named "posts" containing user posts
            firestore.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        // Get the most recent post document
                        val postDocument = documents.documents[0]

                        // Assuming you have a field named "content" in your post document
                        val postContent = postDocument.getString("content")

                        // Update the thread TextView with the most recent post content
                        threadTextView.text = postContent
                    } else {
                        // Handle the case where there are no posts
                        threadTextView.text = "No posts available."
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle failures
                    Toast.makeText(
                        requireActivity(),
                        "Failed to retrieve posts: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
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
}