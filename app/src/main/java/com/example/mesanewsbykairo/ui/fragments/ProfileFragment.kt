package com.example.mesanewsbykairo.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.mesanewsbykairo.ui.LoginActivity
import com.example.mesanewsbykairo.ui.MainActivity
import com.example.mesanewsbykairo.ui.R
import com.example.mesanewsbykairo.utils.deleteAll
import com.example.mesanewsbykairo.utils.getEmail
import com.example.mesanewsbykairo.utils.getToken
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment : Fragment() {
    private var textViewEmail: TextView? = null
    private var btnLogout: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onResume() {
        super.onResume()

        val email = getEmail()
        textViewEmail = activity?.findViewById(R.id.tv_email_logout)
        btnLogout = activity?.findViewById(R.id.btn_logout)

        btnLogout?.setOnClickListener {
            deleteAll()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        textViewEmail?.text = email
    }
}