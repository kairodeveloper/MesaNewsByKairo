package com.example.mesanewsbykairo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    private var textInputEditTextEmail: TextInputEditText? = null
    private var textInputEditTextSenha: TextInputEditText? = null
    private var textInputEditTextConfirmSenha: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Registro"

        textInputEditTextEmail = findViewById(R.id.et_email_sign_up)
        textInputEditTextSenha = findViewById(R.id.et_senha_sign_up)
        textInputEditTextConfirmSenha = findViewById(R.id.et_confirm_senha)

    }

    private fun validateData(): Boolean {
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
            textInputEditTextConfirmSenha?.text.toString().isEmpty() -> {
                textInputEditTextConfirmSenha?.requestFocus()
                Toast.makeText(this, getString(R.string.confirm_password_label), Toast.LENGTH_SHORT).show()
                false
            }
            textInputEditTextSenha?.text.toString()!=textInputEditTextConfirmSenha?.text.toString() -> {
                textInputEditTextConfirmSenha?.requestFocus()
                Toast.makeText(this, getString(R.string.different_passwords_label), Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }

    }

    fun onClick(view: View) {
        if (view.tag=="btn_regiter") {
            if (validateData()) {
                Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}