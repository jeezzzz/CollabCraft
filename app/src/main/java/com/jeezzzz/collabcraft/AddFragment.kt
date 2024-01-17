package com.jeezzzz.collabcraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Replace R.layout.fragment_add with your actual layout resource ID
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // Code for the domain spinner
        val domainItems = ArrayList<DomainItem>()
        domainItems.add(DomainItem("ML"))
        domainItems.add(DomainItem("Android"))
        domainItems.add(DomainItem("Web Development"))
        // Add more domains as needed

        val domainAdapter = DomainAdapter(requireContext(), domainItems)
        val domainSpinner: Spinner = view.findViewById(R.id.spinner_domain)
        domainSpinner.adapter = domainAdapter

        domainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val clickedItem = parent?.getItemAtPosition(position) as DomainItem
                val domain = clickedItem.domainName
                Toast.makeText(requireContext(), "Selected domain: $domain", Toast.LENGTH_SHORT).show()
                // Add logic to filter and display posts based on the selected domain
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        return view
    }
}
