package com.airline.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airline.android.R
import com.airline.android.emailValidator
import com.airline.android.hash
import com.airline.android.model.JsendFail
import com.airline.android.model.UserIdResponse
import com.airline.android.passwordValidator
import com.airline.android.ui.SignUpActivity
import com.airline.android.vm.SignUpViewModel
import com.google.android.material.textfield.TextInputLayout


class SignUpFragment : Fragment() {
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var emailInput: TextView

    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var passwordInput: TextView

    private lateinit var next: Button

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        emailInputLayout = view.findViewById(R.id.email_input_layout)
        emailInput = view.findViewById(R.id.email_input)
        passwordInputLayout = view.findViewById(R.id.password_input_layout)
        passwordInput = view.findViewById(R.id.password_input)
        next = view.findViewById(R.id.next_button)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(SignUpViewModel::class.java)

        next.setOnClickListener { button ->
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

            button.visibility = View.INVISIBLE

            viewModel.tryToSignUp(email, password.hash()).observe(this, Observer { resp ->
                val act = activity as SignUpActivity
                when (resp.status) {
                    "success" -> {
                        viewModel.userId = (resp.data as UserIdResponse).userId
                        // navigate to next fragment
                        act.showPassengerInfoFragment()
                    }
                    "fail" -> act.showSnackbar((resp.data as JsendFail).message)
                    "error" -> act.showSnackbar(resp.message.toString())
                }
                button.visibility = View.VISIBLE
            })
        }

    }

}
