package com.example.mesanewsbykairo.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mesanewsbykairo.models.User
import com.example.mesanewsbykairo.services.RetrofitInitializer
import com.example.mesanewsbykairo.services.beans.LoginBody
import com.example.mesanewsbykairo.services.beans.LoginResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var textInputEditTextEmail: TextInputEditText? = null
    private var textInputEditTextSenha: TextInputEditText? = null
    private var btnLogin: Button? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        textInputEditTextEmail = findViewById(R.id.et_email)
        textInputEditTextSenha = findViewById(R.id.et_senha)
        btnLogin = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.indeterminate_bar)
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
                btnLogin?.visibility = View.GONE
                progressBar?.visibility = View.VISIBLE

                val obj = LoginBody(textInputEditTextEmail?.text.toString(), textInputEditTextSenha?.text.toString())
                val response = RetrofitInitializer().userService().login("application/json", obj)
                response.enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        btnLogin?.visibility = View.VISIBLE
                        progressBar?.visibility = View.GONE
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        val user = response.body()?.token?.let {
                            User(textInputEditTextEmail?.text.toString(), it )
                        }
                        user?.save()

                        val intent = Intent(this@LoginActivity, NewsActivity::class.java)
                        startActivity(intent)
                        this@LoginActivity.finish()
                    }

                })
            }
        } else {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
