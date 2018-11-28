package com.airline.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.airline.android.R
import com.google.android.material.navigation.NavigationView

/**
 * Class, responsible for main page
 *
 * @see R.layout.activity_main - xml layout of page
 */
class MainActivity : AppCompatActivity() {
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

        // setup navigation view
        mNavigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            mDrawerLayout.closeDrawers()

            mToolbar.title = it.title

            // TODO: add fragment swapping here

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
}
