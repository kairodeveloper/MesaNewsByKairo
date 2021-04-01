package com.example.mesanewsbykairo.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private var textInputEditTextEmail: TextInputEditText? = null
    private var textInputEditTextSenha: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        textInputEditTextEmail = findViewById(R.id.et_email)
        textInputEditTextSenha = findViewById(R.id.et_senha)
    }

    private fun validateData() : Boolean {
        return when {
            textInputEditTextEmail?.text.toString().isEmpty() -> {
                textInputEditTextEmail?.requestFocus()
                Toast.makeText(this, getString(R.string.fill_email_label), Toast.LENGTH_SHORT).show()
                false
            }
            textInputEditTextSenha?.text.toString().isEmpty() -> {
                textInputEditTextSenha?.requestFocus()
                Toast.makeText(this, getString(R.string.fill_password_label), Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }

    }

    fun onClick(view: View) {
        if (view.tag == "btn_login") {
            if (validateData()) {
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
            }
        } else {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
