package com.jeezzzz.collabcraft

import com.jeezzzz.collabcraft.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


// DomainAdapter.java


class DomainAdapter(context: Context?, domainList: ArrayList<DomainItem>) :
    ArrayAdapter<DomainItem?>(context!!, 0, domainList!! as List<DomainItem?>) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView!!, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView!!, parent)
    }

    private fun initView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.domain_spinner_item, parent, false)
        }
        val textViewDomain = convertView.findViewById<TextView>(R.id.domain_text_view)
        val currentItem = getItem(position)
        if (currentItem != null) {
            textViewDomain.text = currentItem.domainName
        }
        return convertView
    }
}

