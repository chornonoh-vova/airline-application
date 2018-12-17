package com.airline.android.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airline.android.Const
import com.airline.android.R
import com.airline.android.model.Flight
import com.airline.android.model.Ticket
import com.airline.android.vm.FlightsViewModel
import com.airline.android.vm.TicketsViewModel
import com.google.android.material.snackbar.Snackbar

class FlightDetailsActivity: AppCompatActivity(), CallbackActivity {
    private val mCoordinatorLayout by lazy {
        findViewById<CoordinatorLayout>(R.id.coordinator_layout)
    }

    private val mToolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val mPurchaseButton by lazy {
        findViewById<Button>(R.id.purchase_button)
    }

    private val mRouteId by lazy {
        findViewById<TextView>(R.id.route_id)
    }

    private val mDeparture by lazy {
        findViewById<TextView>(R.id.flight_departure_text)
    }

    private val mArrival by lazy {
        findViewById<TextView>(R.id.flight_arrival_text)
    }

    private lateinit var flightsViewModel: FlightsViewModel
    private lateinit var ticketsViewModel: TicketsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_details)

        mToolbar.title = getText(R.string.flight_activity)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        flightsViewModel = ViewModelProviders.of(this).get(FlightsViewModel::class.java)
        flightsViewModel.errorCallback = errorCallback
        ticketsViewModel = ViewModelProviders.of(this).get(TicketsViewModel::class.java)
        ticketsViewModel.errorCallback = errorCallback

        intent.extras?.let {
            flightsViewModel.flightId = it.getInt(Const.FLIGHT_ID_KEY)
            flightsViewModel.getFlight().observe(this, flightObserver)
        }

        if (ticketsViewModel.purchaseEnabled) {
            mPurchaseButton.visibility = View.VISIBLE
        }

        mPurchaseButton.setOnClickListener {
            ticketsViewModel.purchase(flightsViewModel.flightId).observe(this, Observer {
                // hide button on successful purchase
                showSnackbar("Successfully purchased ticket!")
                mPurchaseButton.visibility = View.GONE
            })
        }
    }

    private val flightObserver = Observer<Flight> {
        mRouteId.text = getString(R.string.route_id_text, it.routeId)
        mDeparture.text = getString(R.string.departure_text, it.departureTime)
        mArrival.text = getString(R.string.arrival_text, it.arrivalTime)
        ticketsViewModel.getAllTickets().observe(this, ticketsObserver)
    }

    private val ticketsObserver = Observer<List<Ticket>> {
        ticketsViewModel.purchaseEnabled = true
        it.forEach { ticket ->
            if (ticket.flightId == flightsViewModel.flightId) {
                ticketsViewModel.purchaseEnabled = false
            }
        }
        if (ticketsViewModel.purchaseEnabled) {
            mPurchaseButton.visibility = View.VISIBLE
        }
    }

    override fun showSnackbar(message: String) =
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show()

    private val errorCallback: (message: String?) -> Unit = {
        showSnackbar(it.toString())
    }
}