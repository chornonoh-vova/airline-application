package com.airline.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airline.android.Const
import com.airline.android.R
import com.airline.android.vm.FlightsViewModel
import com.airline.android.vm.RoutesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_route_details.*

class RouteDetailsActivity: AppCompatActivity(), CallbackActivity {
    private val mCoordinatorLayout by lazy {
        findViewById<CoordinatorLayout>(R.id.coordinator_layout)
    }

    private val mToolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private lateinit var routesViewModel: RoutesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_details)

        mToolbar.title = getText(R.string.routes_activity)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        intent.extras?.let {
            routesViewModel = ViewModelProviders.of(this).get(RoutesViewModel::class.java)
            routesViewModel.errorCallback = errorCallback
            val viewModel = ViewModelProviders.of(this).get(FlightsViewModel::class.java)
            viewModel.errorCallback = errorCallback
            viewModel.routeId.value = it.getInt(Const.ROUTE_ID_KEY)
            routesViewModel.routeId = it.getInt(Const.ROUTE_ID_KEY)
        }

        routesViewModel.getRoute().observe(this, Observer {
            route_to_text.text = it.destinationAirport
            route_from_text.text = it.departureAirport
            route_duration.text = it.duration
            route_price.text = it.price.toString()
        })
    }

    override fun showSnackbar(message: String) =
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show()

    private val errorCallback: (message: String?) -> Unit = {
        showSnackbar(it.toString())
    }
}