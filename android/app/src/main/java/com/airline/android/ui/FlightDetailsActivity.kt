package com.airline.android.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airline.android.Const
import com.airline.android.R
import com.airline.android.getAllChilds
import com.airline.android.model.Flight
import com.airline.android.model.JsendFail
import com.airline.android.vm.FlightsViewModel
import com.airline.android.vm.TicketsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_flight_details.*

class FlightDetailsActivity : AppCompatActivity(), CallbackActivity {
    private val mCoordinatorLayout by lazy {
        findViewById<CoordinatorLayout>(R.id.coordinator_layout)
    }

    private val mToolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val mPurchaseButton by lazy {
        findViewById<Button>(R.id.buy_button)
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

        mPurchaseButton.setOnClickListener {
            val seats = seats_selection.getAllChilds().map { chip -> chip.text.toString() }
            ticketsViewModel.purchase(flightsViewModel.flightId, seats).observe(this, Observer {
                // hide button on successful purchase
                showSnackbar("Successfully purchased ticket!")
                seats_selection.removeAllViews()
                seats_input.setText("")
            })
        }

        add_button.setOnClickListener {
            val seat = seats_input.text.toString().trim()
            hideKeyboard()
            if (!seat.isBlank()) {
                ticketsViewModel.checkTicket(flightsViewModel.flightId, seat).observe(this, Observer { response ->
                    when (response.status) {
                        "success" -> {
                            val chip = Chip(this)
                            chip.text = seat
                            chip.isCloseIconVisible = true
                            chip.isClickable = true
                            chip.isCheckable = false
                            seats_selection.addView(chip as View)
                            chip.setOnCloseIconClickListener {
                                seats_selection.removeView(chip as View)
                            }
                            seats_input.setText("")
                        }
                        "fail" -> showSnackbar((response.data as JsendFail).message)
                        "error" -> showSnackbar(response.message.toString())
                    }
                })
            } else {
                showSnackbar("Seat number cannot be empty!")
            }
        }
    }

    private val flightObserver = Observer<Flight> {
        mRouteId.text = getString(R.string.route_id_text, it.routeId)
        mDeparture.text = getString(R.string.departure_text, it.departureTime)
        mArrival.text = getString(R.string.arrival_text, it.arrivalTime)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun showSnackbar(message: String) =
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show()

    private val errorCallback: (message: String?) -> Unit = {
        showSnackbar(it.toString())
    }
}