package com.example.mesanewsbykairo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.mesanewsbykairo.ui.R

class FiltersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filters, container, false)
    }

    override fun onStart() {
        super.onStart()

        val dropdown: Spinner? = activity?.findViewById(R.id.spinner1)
        val items = arrayOf<String?>("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31")
        val adapter: ArrayAdapter<String>? = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_dropdown_item, items) }
        dropdown?.adapter = adapter
        dropdown?.setSelection(0)

    }

}
