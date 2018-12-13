package com.airline.android.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airline.android.*
import com.airline.android.di.DaggerViewModelInjector
import com.airline.android.di.NetModule
import com.airline.android.di.ViewModelInjector
import com.airline.android.model.JsendFail
import com.airline.android.model.JsendResponse
import com.airline.android.model.LoginResponse
import com.airline.android.net.AirlineApi
import com.airline.android.net.Credentials
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private val injector: ViewModelInjector =
        DaggerViewModelInjector.builder()
            .netModule(NetModule())
            .build()

    private val emailInputLayout by lazy {
        findViewById<TextInputLayout>(R.id.email_input_layout)
    }

    private val emailInput by lazy {
        findViewById<TextView>(R.id.email_input)
    }

    private val passwordInputLayout by lazy {
        findViewById<TextInputLayout>(R.id.password_input_layout)
    }

    private val passwordInput by lazy {
        findViewById<TextView>(R.id.password_input)
    }

    private val signInButton by lazy {
        findViewById<Button>(R.id.sign_in_button)
    }

    private val signUpButton by lazy {
        findViewById<Button>(R.id.sign_up_button)
    }

    @Inject
    lateinit var api: AirlineApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        injector.inject(this)

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInButton.setOnClickListener {
            val email = emailInput.text.toString().trim()

            if (email.isBlank() || !emailValidator.matches(email)) {
                emailInputLayout.error = "Email is invalid or empty"
                return@setOnClickListener
            }

            val password = passwordInput.text.toString().trim()

            if (password.isBlank() || !passwordValidator.matches(password)) {
                passwordInputLayout.error = "Password must be at least 6 symbols long"
                return@setOnClickListener
            }

            emailInputLayout.error = ""
            passwordInputLayout.error = ""

            api.login(LoginRequest(email, password.hash())).enqueue(callback)
        }
    }

    private val callback = object : Callback<JsendResponse> {
        override fun onFailure(call: Call<JsendResponse>, t: Throwable) {
            Toast.makeText(this@LoginActivity, "Network error: " + t.message, Toast.LENGTH_LONG).show()
        }

        override fun onResponse(call: Call<JsendResponse>, response: Response<JsendResponse>) {
            val body = response.body()
            if (body != null) {
                when (body.status) {
                    "success" -> {
                        val data = body.data as LoginResponse
                        (application as App).saveCredentials(Credentials("Basic", data.sessionKey))
                        (application as App).saveUserId(data.userId)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    "fail" -> {
                        Toast.makeText(this@LoginActivity, (body.data as JsendFail).message, Toast.LENGTH_LONG).show()
                    }
                    "error" -> {
                        Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
