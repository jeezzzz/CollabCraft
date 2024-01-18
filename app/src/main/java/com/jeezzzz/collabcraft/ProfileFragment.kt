package com.jeezzzz.collabcraft

import android.os.Bundle
import android.util.Log
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val use = view.findViewById<TextView>(R.id.name)

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
                        use?.text = userName
                    } else {
                        Log.d("MyApp", "Document does not exist or is empty")
                        Toast.makeText(requireActivity(), "Failed to load user", Toast.LENGTH_SHORT)
                            .show()
                    }
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
}