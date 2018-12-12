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
import com.airline.android.model.JsendFail
import com.airline.android.model.UserIdResponse
import com.airline.android.ui.SignUpActivity
import com.airline.android.vm.SignUpViewModel
import com.google.android.material.textfield.TextInputLayout

class PassengerInfoFragment: Fragment() {
    private lateinit var firstNameInputLayout: TextInputLayout
    private lateinit var firstNameInput: TextView

    private lateinit var lastNameInputLayout: TextInputLayout
    private lateinit var lastNameInput: TextView

    private lateinit var addressInputLayout: TextInputLayout
    private lateinit var addressInput: TextView

    private lateinit var phoneInputLayout: TextInputLayout
    private lateinit var phoneInput: TextView

    private lateinit var complete: Button

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.passenger_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firstNameInputLayout = view.findViewById(R.id.first_name_input_layout)
        firstNameInput = view.findViewById(R.id.first_name_input)
        lastNameInputLayout = view.findViewById(R.id.last_name_input_layout)
        lastNameInput = view.findViewById(R.id.last_name_input)
        addressInputLayout = view.findViewById(R.id.address_input_layout)
        addressInput = view.findViewById(R.id.address_input)
        phoneInputLayout = view.findViewById(R.id.phone_input_layout)
        phoneInput = view.findViewById(R.id.phone_input)
        complete = view.findViewById(R.id.complete_button)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(SignUpViewModel::class.java)

        complete.setOnClickListener { button ->
            val firstName = getText(firstNameInput)
            val lastName = getText(lastNameInput)
            val address = getText(addressInput)
            val phone = getText(phoneInput)
            if (firstName.isBlank()) {
                firstNameInputLayout.error = "First name cannot be empty"
                return@setOnClickListener
            }
            if (lastName.isBlank()) {
                lastNameInputLayout.error = "Last name cannot be empty"
                return@setOnClickListener
            }
            if (address.isBlank()) {
                addressInputLayout.error = "Address cannot be empty"
                return@setOnClickListener
            }
            if (phone.isBlank()) {
                phoneInputLayout.error = "Phone cannot be empty"
                return@setOnClickListener
            }
            firstNameInputLayout.error = ""
            lastNameInputLayout.error = ""
            addressInputLayout.error = ""
            phoneInputLayout.error = ""

            button.visibility = View.INVISIBLE

            viewModel.addPassengerInfo(firstName, lastName, address, phone).observe(this, Observer { resp ->
                val act = activity as SignUpActivity
                when(resp.status) {
                    "success" -> {
                        // navigate back to login
                        act.onBackPressed()
                    }
                    "fail" -> act.showSnackbar((resp.data as JsendFail).message)
                    "error" -> act.showSnackbar(resp.message.toString())
                }
                button.visibility = View.VISIBLE
            })
        }
    }

    private fun getText(t: TextView): String = t.text.toString().trim()
}