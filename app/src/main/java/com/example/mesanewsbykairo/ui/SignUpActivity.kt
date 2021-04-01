package com.example.mesanewsbykairo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.mesanewsbykairo.models.User
import com.example.mesanewsbykairo.services.RetrofitInitializer
import com.example.mesanewsbykairo.services.beans.LoginResponse
import com.example.mesanewsbykairo.services.beans.SignUpBody
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private var textInputEditTextName: TextInputEditText? = null
    private var textInputEditTextEmail: TextInputEditText? = null
    private var textInputEditTextSenha: TextInputEditText? = null
    private var textInputEditTextConfirmSenha: TextInputEditText? = null
    private var btnSignUp: Button? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Registro"

        textInputEditTextName = findViewById(R.id.et_name_sign_up)
        textInputEditTextEmail = findViewById(R.id.et_email_sign_up)
        textInputEditTextSenha = findViewById(R.id.et_senha_sign_up)
        textInputEditTextConfirmSenha = findViewById(R.id.et_confirm_senha)
        btnSignUp = findViewById(R.id.btn_regiter)
        progressBar = findViewById(R.id.indeterminate_bar_sign_up)
    }

    private fun validateData(): Boolean {
        return when {
            textInputEditTextName?.text.toString().isEmpty() -> {
                textInputEditTextName?.requestFocus()
                Toast.makeText(this, getString(R.string.fill_name_label), Toast.LENGTH_SHORT).show()
                false
            }
            textInputEditTextEmail?.text.toString().isEmpty() -> {
                textInputEditTextEmail?.requestFocus()
                Toast.makeText(this, getString(R.string.fill_email_label), Toast.LENGTH_SHORT)
                    .show()
                false
            }
            textInputEditTextSenha?.text.toString().isEmpty() -> {
                textInputEditTextSenha?.requestFocus()
                Toast.makeText(this, getString(R.string.fill_password_label), Toast.LENGTH_SHORT)
                    .show()
                false
            }
            textInputEditTextConfirmSenha?.text.toString().isEmpty() -> {
                textInputEditTextConfirmSenha?.requestFocus()
                Toast.makeText(this, getString(R.string.confirm_password_label), Toast.LENGTH_SHORT)
                    .show()
                false
            }
            textInputEditTextSenha?.text.toString() != textInputEditTextConfirmSenha?.text.toString() -> {
                textInputEditTextConfirmSenha?.requestFocus()
                Toast.makeText(
                    this,
                    getString(R.string.different_passwords_label),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            else -> {
                true
            }
        }

    }

    fun onClick(view: View) {
        btnSignUp?.visibility = View.GONE
        progressBar?.visibility = View.VISIBLE

        if (view.tag == "btn_regiter") {
            if (validateData()) {
                val obj = SignUpBody(
                    textInputEditTextName?.text.toString(),
                    textInputEditTextEmail?.text.toString(),
                    textInputEditTextSenha?.text.toString()
                )
                val response = RetrofitInitializer().userService().signUp("application/json", obj)
                response.enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        btnSignUp?.visibility = View.VISIBLE
                        progressBar?.visibility = View.GONE
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.code() == 201) {
                            val user = response.body()?.token?.let {
                                User(textInputEditTextEmail?.text.toString(), it)
                            }

                            user?.save()

                            val intent = Intent(this@SignUpActivity, NewsActivity::class.java)
                            startActivity(intent)
                            this@SignUpActivity.finish()

                        } else {
                            Toast.makeText(this@SignUpActivity, response.message(), Toast.LENGTH_SHORT).show()
                            btnSignUp?.visibility = View.VISIBLE
                            progressBar?.visibility = View.GONE
                        }
                    }

                })
            }
        }
    }
}