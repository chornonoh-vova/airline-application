package com.airline.android.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.airline.android.App
import com.airline.android.R
import com.airline.android.ui.fragments.FlightsFragment
import com.airline.android.ui.fragments.HomeFragment
import com.airline.android.ui.fragments.RoutesFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

/**
 * Class, responsible for main page
 *
 * @see R.layout.activity_main - xml layout of page
 */
class MainActivity : AppCompatActivity(), CallbackActivity {
    private val mToolbar by lazy {
        findViewById<Toolbar>(R.id.main_activity_toolbar)
    }
    private val mNavigationView by lazy {
        findViewById<NavigationView>(R.id.main_navigation_view)
    }
    // layouts
    private val mDrawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.main_drawer_layout)
    }
    private val mCoordinatorLayout by lazy {
        findViewById<CoordinatorLayout>(R.id.main_coord_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val id = (application as App).loadUserId()

        // setup navigation view
        mNavigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.logout_action) {
                return@setNavigationItemSelectedListener false
            }

            it.isChecked = true
            mDrawerLayout.closeDrawers()

            mToolbar.title = it.title

            val fragment = when(it.itemId) {
                R.id.flights_action -> FlightsFragment()
                R.id.home_action -> HomeFragment()
                R.id.routes_action -> RoutesFragment()
                else -> Fragment()
            }

            showFragment(fragment)

            true
        }

        // setup toolbar
        mToolbar.title = getText(R.string.home_action)
        setSupportActionBar(mToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        // setting home action checked by default
        mNavigationView.setCheckedItem(R.id.home_action)

        // setting home fragment activated by default
        showHomeFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showHomeFragment() = showFragment(HomeFragment())

    fun showFlightsFragment() = showFragment(FlightsFragment())

    fun showRoutesFragment() = showFragment(RoutesFragment())

    private fun showFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            this.replace(R.id.main_content_container, fragment)
            this.commit()
        }
    }

    override fun showSnackbar(message: String) =
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show()
}
