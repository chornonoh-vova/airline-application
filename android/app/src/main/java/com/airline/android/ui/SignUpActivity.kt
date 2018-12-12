package com.airline.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.airline.android.R
import com.airline.android.ui.fragments.PassengerInfoFragment
import com.airline.android.ui.fragments.SignUpFragment
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity() {
    private val mCoordinatorLayout by lazy {
        findViewById<CoordinatorLayout>(R.id.coordinator_layout)
    }

    private var currentFragment = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt("savedFragmentIndex", 0)
        }

        when(currentFragment) {
            0 -> showSignUpFragment()
            1 -> showPassengerInfoFragment()
            else -> showSignUpFragment()
        }

    }

    fun showSignUpFragment() = showFragment(SignUpFragment())

    fun showPassengerInfoFragment() = showFragment(PassengerInfoFragment())

    private fun showFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            this.replace(R.id.content, fragment)
            this.commit()
        }
    }

    fun showSnackbar(message: String) =
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show()
}
